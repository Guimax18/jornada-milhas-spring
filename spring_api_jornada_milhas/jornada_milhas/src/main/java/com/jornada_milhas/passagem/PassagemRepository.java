package com.jornada_milhas.passagem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PassagemRepository extends JpaRepository<Passagem, Integer> {
    @Query("""
            SELECT new com.jornada_milhas.passagem.MinMaxPriceDto(MIN(p.precoIda), MAX(p.precoIda))
            FROM Passagem p WHERE p.precoIda > 0
                """)
    MinMaxPriceDto findMinAndMaxPrecoIda();
}
