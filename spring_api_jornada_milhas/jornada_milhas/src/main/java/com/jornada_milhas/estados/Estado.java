package com.jornada_milhas.estados;

import java.util.List;

import com.jornada_milhas.passagem.Passagem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Estado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String sigla;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Passagem> passagensComoOrigem;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Passagem> passagensComoDestino;
}
