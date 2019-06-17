package com.starwars.resistence.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.resistence.repository.InventarioRepository;
import com.starwars.resistence.repository.RebeldeRepository;
import com.starwars.resistence.exception.ResourceNotFoundException;
import com.starwars.resistence.model.Rebelde;
import com.starwars.resistence.model.RebeldeAdd;

@RestController
public class RebeldeController {

	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private InventarioRepository inventarioRepository;
	
	
	@PostMapping("/rebeldes")
	public RebeldeAdd addRebelde(@Valid @RequestBody RebeldeAdd rebelde) {
		
		if(rebelde.getRebelde() == null) {
			throw new ResourceNotFoundException("Rebelde não informado!");
		}
		if(rebelde.getInventario() == null || rebelde.getInventario().size() == 0) {
			throw new ResourceNotFoundException("Inventário não informado!");
		}
		Rebelde rbl = rebelde.getRebelde();		
		rbl = rebeldeRepository.save(rbl);
		rebelde.setRebelde(rbl);
		
		for (int i = 0; i < rebelde.getInventario().size(); i++) {
			rebelde.getInventario().get(i).setRebelde(rbl);
			rebelde.getInventario().set(i, inventarioRepository.save(rebelde.getInventario().get(i)));				
		}
		
		return rebelde;
	}
	
	@GetMapping("/rebeldes")
	public Page<Rebelde> getRebeldes(Pageable pageable) {
		return rebeldeRepository.findAll(pageable);
	}
}
