package com.jornada_milhas.users;

import java.time.LocalDate;

import com.jornada_milhas.estados.Estado;

public record UserDto(
    Integer id,
    String nome,
    LocalDate nascimento,
    String cpf,
    String telefone,
    String email,
    String senha,
    String genero,
    String cidade,
    Estado estado
) {
    
}
