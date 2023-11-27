package com.jornada_milhas.promocoes;

import java.math.BigDecimal;

public record PromocoesDto(
        String destino,
        String imagem,
        BigDecimal preco) {

}
