package com.starwars.resistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.enumeration.Item;
import com.starwars.resistence.model.Inventario;
import com.starwars.resistence.model.InventarioReport;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
	Page<Inventario> findByRebeldeId(Long rebeldeId, Pageable pageable);
	Optional<Inventario> findByIdAndRebeldeId(Long id, Long rebeldeId);
	Optional<Inventario> findByItemAndRebeldeId(Item item, Long rebeldeId);
	
	@Query(
			value = "SELECT I.ITEM, SUM(I.QUANTIDADE) AS QUANTIDADE FROM INVENTARIOS I WHERE I.REBELDE_ID NOT IN (SELECT "+ 
					"T.REBELDE_ID FROM REPORTA_TRAIDORES T GROUP BY T.REBELDE_ID HAVING COUNT(ID) >= 3) GROUP BY I.ITEM",
			nativeQuery = true
	)
	List<InventarioReport> getRecursosRebeldes();
	
	@Query(
			value = "SELECT I.ITEM, SUM(I.QUANTIDADE) AS QUANTIDADE FROM INVENTARIOS I WHERE I.REBELDE_ID IN (SELECT "+ 
					"T.REBELDE_ID FROM REPORTA_TRAIDORES T GROUP BY T.REBELDE_ID HAVING COUNT(ID) >= 3) GROUP BY I.ITEM",
			nativeQuery = true
	)
	List<InventarioReport> getRecursosTraidores();

}
