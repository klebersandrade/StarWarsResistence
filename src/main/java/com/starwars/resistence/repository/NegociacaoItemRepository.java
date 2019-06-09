package com.starwars.resistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.NegociacaoItem;

@Repository
public interface NegociacaoItemRepository extends JpaRepository<NegociacaoItem, Long> {

}
