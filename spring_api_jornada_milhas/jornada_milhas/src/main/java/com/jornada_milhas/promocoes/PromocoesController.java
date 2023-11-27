package com.jornada_milhas.promocoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/promocoes")
public class PromocoesController {
    
    @Autowired
    private PromocoesService service;

    @GetMapping("/todos")
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(service.findOne(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody PromocoesDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@RequestParam Integer id, @RequestBody PromocoesDto dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
