package com.starwars.resistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.Localizacao;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
	Page<Localizacao> findByRebeldeId(Long rebeldeId, Pageable pageable);
	Optional<Localizacao> findByIdAndRebeldeId(Long id, Long rebeldeId);
}
