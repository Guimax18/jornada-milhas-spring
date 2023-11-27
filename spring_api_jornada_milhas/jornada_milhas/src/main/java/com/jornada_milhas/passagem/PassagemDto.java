package com.jornada_milhas.passagem;

import java.math.BigDecimal;

import com.jornada_milhas.companhias.Companhia;
import com.jornada_milhas.estados.Estado;

public record PassagemDto(

        TipoPassagem tipo,
        BigDecimal precoIda,
        BigDecimal precoVolta,
        BigDecimal taxaEmbarque,
        Integer conexoes,
        Long tempoVoo,
        Estado origem,
        Estado destino,
        Companhia companhia) {

}
