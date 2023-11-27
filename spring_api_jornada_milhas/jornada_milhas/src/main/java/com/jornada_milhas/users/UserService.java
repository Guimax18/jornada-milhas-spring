package com.jornada_milhas.users;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Users findOne(String email) {
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public Users create(UserDto user) {
        if (findOne(user.email()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado");
        }
        Users u = new Users();
        BeanUtils.copyProperties(user, u);
        return repository.save(u);
    }

    public Users update(String email, UserDto user) {
        Users u = repository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Integer id = u.getId();
        BeanUtils.copyProperties(user, u);
        u.setId(id);
        return repository.save(u);
    }
}
