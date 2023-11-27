package com.jornada_milhas.passagem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jornada_milhas.companhias.Companhia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PassagemService {

    @Autowired
    private PassagemRepository repository;

    @PersistenceContext
    private EntityManager manager;

    public List<Passagem> findAll() {
        var list = repository.findAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há nenhuma passagem");
        }
        return list;
    }

    public Passagem findOne(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passagem não encontrado"));
    }

    public Passagem update(Integer id, PassagemDto dto) {
        Passagem estado = findOne(id);
        BeanUtils.copyProperties(dto, estado);
        estado.setId(id);
        return estado;
    }

    public void delete(Integer id) {
        findOne(id);
        repository.deleteById(id);
    }

    public MinMaxPriceDto findMinAndMaxPrecoIda() {
        return repository.findMinAndMaxPrecoIda();
    }

    public List<ResultadoDto> search(QueryPassagemDto dto) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Passagem> query = builder.createQuery(Passagem.class);
        Root<Passagem> root = query.from(Passagem.class);

        root.fetch("origem");
        root.fetch("destino");
        root.fetch("companhia");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.gt(root.get("passagem.id"), 0));

        if (dto.tipo() != null) {
            predicates.add(builder.equal(root.get("passagem.tipo"), dto.tipo()));
        }

        if (dto.origemId() != null) {
            predicates.add(builder.equal(root.get("passagem.origemId"), dto.origemId()));
        }

        if (dto.destinoId() != null) {
            predicates.add(builder.equal(root.get("passagem.destinoId"), dto.destinoId()));
        }

        if (dto.minMaxPrice().min() != null) {
            predicates.add(builder.ge(root.get("passagem.precoIda"), dto.minMaxPrice().min()));
        }

        if (dto.minMaxPrice().max() != null) {
            predicates.add(builder.le(root.get("passagem.precoIda"), dto.minMaxPrice().max()));
        }

        if (dto.conexoes() >= 0) {
            predicates.add(dto.conexoes() > 2
                    ? builder.ge(root.get("passagem.conexoes"), dto.conexoes())
                    : builder.equal(root.get("passagem.conexoes"), dto.conexoes()));
        }

        if (dto.companhiasId() != null && !dto.companhiasId().isEmpty()) {
            dto.companhiasId().forEach(id -> {
                Join<Passagem, Companhia> join = root.join("companhia");
                predicates.add(builder.equal(join.get("id"), id));
            });
        }

        query.where(predicates.toArray((new Predicate[0])));

        TypedQuery<Passagem> typedQuery = manager.createQuery(query);

        int pagina = dto.pagina() <= 0 ? 1 : dto.pagina();
        int porPagina = dto.porPagina();
        int offset = (pagina - 1) * porPagina;

        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(porPagina);

        List<Passagem> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(Passagem.class)));
        Long total = manager.createQuery(countQuery).getSingleResult();

        int ultimaPagina = (int) Math.ceil((double) total / porPagina);
        int paginaAtual = dto.pagina();

        List<ResultadoDto> resultado = new ArrayList<>();

        for (Passagem passagem : resultList) {
            BigDecimal totalPassagens = BigDecimal.ZERO;
            List<OrcamentoDto> orcamento = new ArrayList<>();

            if (dto.passageirosAdultos() > 0) {
                var ida = passagem.getPrecoIda().multiply(BigDecimal.valueOf(dto.passageirosAdultos()));
                var volta = dto.somenteIda() ? BigDecimal.ZERO
                        : passagem.getPrecoVolta().multiply(BigDecimal.valueOf(dto.passageirosAdultos()));
                var preco = ida.add(volta);
                totalPassagens = totalPassagens.add(preco).add(passagem.getTaxaEmbarque());

                OrcamentoDto detalhes = new OrcamentoDto(
                        dto.passageirosAdultos() + " adulto" +
                                (dto.passageirosAdultos() > 1 ? "s" : "") +
                                ", " + passagem.getTipo().toString().toLowerCase(),
                        preco,
                        passagem.getTaxaEmbarque(),
                        preco.add(passagem.getTaxaEmbarque()));

                orcamento.add(detalhes);
            }
            if (dto.passageirosCriancas() > 0) {
                var ida = passagem.getPrecoIda().multiply(BigDecimal.valueOf(dto.passageirosCriancas()));
                var volta = dto.somenteIda() ? BigDecimal.ZERO
                        : passagem.getPrecoVolta().multiply(BigDecimal.valueOf(dto.passageirosCriancas()));
                var preco = ida.add(volta);
                totalPassagens = totalPassagens.add(preco).add(passagem.getTaxaEmbarque());

                OrcamentoDto detalhes = new OrcamentoDto(
                        dto.passageirosCriancas() + " crianças" +
                                (dto.passageirosCriancas() > 1 ? "s" : "") +
                                ", " + passagem.getTipo().toString().toLowerCase(),
                        preco,
                        passagem.getTaxaEmbarque(),
                        preco.add(passagem.getTaxaEmbarque()));

                orcamento.add(detalhes);
            }
            if (dto.passageirosCriancas() > 0) {
                var ida = passagem.getPrecoIda().multiply(BigDecimal.valueOf(dto.passageirosCriancas()));
                var volta = dto.somenteIda() ? BigDecimal.ZERO
                        : passagem.getPrecoVolta().multiply(BigDecimal.valueOf(dto.passageirosCriancas()));
                var preco = ida.add(volta);
                totalPassagens = totalPassagens.add(preco).add(passagem.getTaxaEmbarque());

                OrcamentoDto detalhes = new OrcamentoDto(
                        dto.passageirosCriancas() + " crianças" +
                                (dto.passageirosCriancas() > 1 ? "s" : "") +
                                ", " + passagem.getTipo().toString().toLowerCase(),
                        preco,
                        passagem.getTaxaEmbarque(),
                        preco.add(passagem.getTaxaEmbarque()));

                orcamento.add(detalhes);
            }
            if (dto.passageirosBebes() > 0) {
                var ida = passagem.getPrecoIda().multiply(BigDecimal.valueOf(dto.passageirosBebes()));
                var volta = dto.somenteIda() ? BigDecimal.ZERO
                        : passagem.getPrecoVolta().multiply(BigDecimal.valueOf(dto.passageirosBebes()));
                var preco = ida.add(volta);
                totalPassagens = totalPassagens.add(preco).add(passagem.getTaxaEmbarque());

                OrcamentoDto detalhes = new OrcamentoDto(
                        dto.passageirosBebes() + " bebês" +
                                (dto.passageirosBebes() > 1 ? "s" : "") +
                                ", " + passagem.getTipo().toString().toLowerCase(),
                        preco,
                        passagem.getTaxaEmbarque(),
                        preco.add(passagem.getTaxaEmbarque()));

                orcamento.add(detalhes);
            }
            ResultadoDto resultadoDto = new ResultadoDto(
                    passagem,
                    dto.dataIda(),
                    dto.dataVolta() != null ? dto.dataVolta() : null,
                    orcamento,
                    totalPassagens.intValue());

            resultado.add(resultadoDto);
        }
        return resultado;
    }
}
