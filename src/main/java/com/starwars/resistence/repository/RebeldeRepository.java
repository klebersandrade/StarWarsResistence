package com.starwars.resistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.Rebelde;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {

}
