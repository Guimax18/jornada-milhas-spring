package com.jornada_milhas.passagem;

import java.math.BigDecimal;

import com.jornada_milhas.companhias.Companhia;
import com.jornada_milhas.estados.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TipoPassagem tipo;
    private BigDecimal precoIda;
    private BigDecimal precoVolta;
    private BigDecimal taxaEmbarque;
    private Integer conexoes;
    private Long tempoVoo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estado origem;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estado destino;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Companhia companhia;
}
