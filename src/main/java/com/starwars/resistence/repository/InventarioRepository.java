package com.starwars.resistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.enumeration.Item;
import com.starwars.resistence.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
	Page<Inventario> findByRebeldeId(Long rebeldeId, Pageable pageable);
	Optional<Inventario> findByIdAndRebeldeId(Long id, Long rebeldeId);
	Optional<Inventario> findByItemAndRebeldeId(Item item, Long rebeldeId);

}
