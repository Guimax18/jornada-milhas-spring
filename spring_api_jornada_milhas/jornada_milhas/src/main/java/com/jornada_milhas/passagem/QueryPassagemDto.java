package com.jornada_milhas.passagem;

import java.time.LocalDate;
import java.util.List;

public record QueryPassagemDto(
        Boolean somenteIda,
        Integer passageirosAdultos,
        Integer passageirosCriancas,
        Integer passageirosBebes,
        TipoPassagem tipo,
        String turno,
        Integer origemId,
        List<Integer> companhiasId,
        Integer destinoId,
        MinMaxPriceDto minMaxPrice,
        Integer conexoes,
        Long tempoVoo,
        LocalDate dataIda,
        LocalDate dataVolta,
        Integer pagina,
        Integer porPagina) {

}
