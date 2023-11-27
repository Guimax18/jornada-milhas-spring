package com.jornada_milhas.depoimentos;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DepoimentoService {
    
    @Autowired
    private DepoimentoRepository repository;

    public List<Depoimento> findAll() {
        var list = repository.findAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há nenhum depoimento");
        }
        return list;
    }

    public Depoimento findOne(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Depoimento não encontrado"));
    }

    public Depoimento create(DepoimentoDto dto) {
        Depoimento depoimento = new Depoimento();
        BeanUtils.copyProperties(dto, depoimento);
        repository
                .findOne(Example.of(depoimento))
                .ifPresent(e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Depoimento já cadastrado");
                });
        return repository.save(depoimento);
    }

    public Depoimento update(Integer id, DepoimentoDto dto) {
        Depoimento depoimento = findOne(id);
        BeanUtils.copyProperties(dto, depoimento);
        depoimento.setId(id);
        return depoimento;
    }

    public void delete(Integer id) {
        findOne(id);
        repository.deleteById(id);
    }
}
