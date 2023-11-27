package com.jornada_milhas.passagem;

import java.math.BigDecimal;

public record OrcamentoDto(
        String descricao,
        BigDecimal preco,
        BigDecimal taxaEmbarque,
        BigDecimal total) {
}
