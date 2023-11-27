package com.jornada_milhas.passagem;

import java.time.LocalDate;
import java.util.List;

public record ResultadoDto(
        Passagem passagem,
        LocalDate dataIda,
        LocalDate dataVolta,
        List<OrcamentoDto> orcamento,
        Integer total) {

}
