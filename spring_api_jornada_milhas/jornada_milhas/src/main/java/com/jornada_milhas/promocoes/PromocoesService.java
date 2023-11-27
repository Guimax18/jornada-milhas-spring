package com.jornada_milhas.promocoes;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PromocoesService {

    @Autowired
    private PromocoesRepository repository;

    public List<Promocoes> findAll() {
        var list = repository.findAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há nenhum promoção");
        }
        return list;
    }

    public Promocoes findOne(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promoção não encontrado"));
    }

    public Promocoes create(PromocoesDto dto) {
        Promocoes promocoes = new Promocoes();
        BeanUtils.copyProperties(dto, promocoes);
        repository
                .findOne(Example.of(promocoes))
                .ifPresent(e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Promoção já existente");
                });
        return repository.save(promocoes);
    }

    public Promocoes update(Integer id, PromocoesDto dto) {
        Promocoes promocoes = findOne(id);
        BeanUtils.copyProperties(dto, promocoes);
        promocoes.setId(id);
        return promocoes;
    }

    public void delete(Integer id) {
        findOne(id);
        repository.deleteById(id);
    }
}
