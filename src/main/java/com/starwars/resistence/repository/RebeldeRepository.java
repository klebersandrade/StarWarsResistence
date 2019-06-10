package com.starwars.resistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.Rebelde;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {
	@Query(
			value = "SELECT ((TRAIDORES * 1.00) / (REBELDES * 1.00)) * 100.00 AS PORC_TRAIDORES FROM "+
					"(SELECT COUNT(*) AS REBELDES, "+ 
					"(SELECT COUNT(*) FROM "+
					"(SELECT REBELDE_ID, COUNT(ID) AS REPORT FROM REPORTA_TRAIDORES "+ 
					"GROUP BY REBELDE_ID) AS REBELDE WHERE REPORT >= 3) AS TRAIDORES "+
					"FROM REBELDES) AS CALCULO",
			nativeQuery = true
	)
	Double getPorcTraidores();
	
	@Query(
			value = "SELECT (((REBELDES - TRAIDORES) * 1.00) / (REBELDES * 1.00)) * 100.00 AS PORC_TRAIDORES FROM "+
					"(SELECT COUNT(*) AS REBELDES, "+ 
					"(SELECT COUNT(*) FROM "+
					"(SELECT REBELDE_ID, COUNT(ID) AS REPORT FROM REPORTA_TRAIDORES "+ 
					"GROUP BY REBELDE_ID) AS REBELDE WHERE REPORT >= 3) AS TRAIDORES "+
					"FROM REBELDES) AS CALCULO",
			nativeQuery = true
	)
	Double getPorcRebeldes();
		
	@Query(
			value = "SELECT COUNT(R.*) AS QTD FROM REBELDES R WHERE R.ID NOT IN "+
					"(SELECT T.REBELDE_ID FROM REPORTA_TRAIDORES T "+
					"GROUP BY T.REBELDE_ID "+
					"HAVING COUNT(ID) >=3)",
			nativeQuery = true
	)
	Integer getQtdRebeldesValidos();
}
