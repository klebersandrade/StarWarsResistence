package com.starwars.resistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.ReportaTraidor;

@Repository
public interface ReportaTraidorRepository extends JpaRepository<ReportaTraidor, Long> {
	Page<ReportaTraidor> findByRebeldeId(Long rebeldeId, Pageable pageable);
	Optional<ReportaTraidor> findByIdAndRebeldeId(Long id, Long rebeldeId);
		
	@Query(
			value = "SELECT count(t.*) FROM reporta_traidores t WHERE t.delator_id = ?1 AND t.rebelde_id = ?2",
			nativeQuery = true
	)
	Long findIfReportou(Long delatorId, Long rebeldeId);
}
