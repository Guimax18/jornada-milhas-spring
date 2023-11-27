package com.jornada_milhas.estados;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<Estado> findAll() {
        var list = repository.findAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há nenhum estado");
        }
        return list;
    }

    public Estado findOne(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não encontrado"));
    }

    public Estado create(EstadoDto dto) {
        Estado estado = new Estado();
        BeanUtils.copyProperties(dto, estado);
        repository
                .findOne(Example.of(estado))
                .ifPresent(e -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado já cadastrado");
                });
        return repository.save(estado);
    }

    public Estado update(Integer id, EstadoDto dto) {
        Estado estado = findOne(id);
        BeanUtils.copyProperties(dto, estado);
        estado.setId(id);
        return estado;
    }

    public void delete(Integer id) {
        findOne(id);
        repository.deleteById(id);
    }
}
