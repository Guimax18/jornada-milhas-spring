package com.jornada_milhas.companhias;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompanhiaService {
    
    @Autowired
    private CompanhiaRepository repository;

    public List<Companhia> findAll() {
        var list = repository.findAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há nenhum companhia");
        }
        return list;
    }

    public Companhia findOne(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Companhia não encontrado"));
    }

    public Companhia create(CompanhiaDto dto) {
        Companhia companhia = new Companhia();
        BeanUtils.copyProperties(dto, companhia);
        repository
                .findOne(Example.of(companhia))
                .ifPresent(e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Companhia já cadastrado");
                });
        return repository.save(companhia);
    }

    public Companhia update(Integer id, CompanhiaDto dto) {
        Companhia companhia = findOne(id);
        BeanUtils.copyProperties(dto, companhia);
        companhia.setId(id);
        return companhia;
    }

    public void delete(Integer id) {
        findOne(id);
        repository.deleteById(id);
    }
}
