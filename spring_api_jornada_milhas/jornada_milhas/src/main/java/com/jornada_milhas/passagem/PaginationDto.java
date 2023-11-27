package com.jornada_milhas.passagem;

import java.util.List;

public record PaginationDto(
        Integer paginaAtual,
        Integer ultimaPagina,
        Integer total,
        MinMaxPriceDto minAndMaxPrice,
        List<PassagemDto> resultado) {

}