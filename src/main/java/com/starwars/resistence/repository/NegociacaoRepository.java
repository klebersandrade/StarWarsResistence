package com.starwars.resistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwars.resistence.model.Negociacao;

@Repository
public interface NegociacaoRepository extends JpaRepository<Negociacao, Long> {

}
