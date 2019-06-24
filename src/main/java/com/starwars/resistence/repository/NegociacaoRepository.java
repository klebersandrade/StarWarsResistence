package com.starwars.resistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.Negociacao;
import com.starwars.resistence.model.NegociacaoView;

@Repository
public interface NegociacaoRepository extends JpaRepository<Negociacao, Long> {

@Query(
		value = "SELECT N.*, R1.NOME AS COMPRADOR_NOME, R2.NOME \r\n" + 
				"AS VENDEDOR_NOME FROM NEGOCIACOES N \r\n" + 
				"INNER JOIN REBELDES R1 ON R1.ID = N.COMPRADOR_ID\r\n" + 
				"INNER JOIN REBELDES R2 ON R2.ID = N.VENDEDOR_ID",
		nativeQuery = true
)
List<NegociacaoView> getNegociacoes();

}
