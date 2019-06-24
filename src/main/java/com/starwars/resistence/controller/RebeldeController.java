package com.starwars.resistence.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.resistence.repository.InventarioRepository;
import com.starwars.resistence.repository.RebeldeRepository;
import com.starwars.resistence.repository.ReportaTraidorRepository;
import com.starwars.resistence.exception.ResourceNotFoundException;
import com.starwars.resistence.model.Inventario;
import com.starwars.resistence.model.Rebelde;
import com.starwars.resistence.model.RebeldeAdd;

@RestController
public class RebeldeController {

	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private InventarioRepository inventarioRepository;
	
	@Autowired
	private ReportaTraidorRepository reportaTraidorRepository;
	
	
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
	public List<Rebelde> getRebeldes(Pageable pageable) {
		List<Rebelde> lista = rebeldeRepository.findAll(pageable).getContent();
		for (int i = 0; i < lista.size(); i++) {
			Rebelde reb = lista.get(i);
			reb.setTraidor(reportaTraidorRepository.findByRebeldeId(reb.getId(), null).getContent().size() >= 3);
		}
		return lista;
	}
	
	@GetMapping("/rebeldes/{id}")
	public Optional<Rebelde> getRebelde(@PathVariable Long id) {
		return rebeldeRepository.findById(id);
	}
	
	@GetMapping("/getrebeldes")
	public List<Rebelde> getRebeldes() {
		return rebeldeRepository.getRebeldes();
	}
	
	@GetMapping("/gettraidores")
	public List<Rebelde> getTraidores() {
		return rebeldeRepository.getTraidores();
	}
	
	@GetMapping("/getinventario/{id}")
	public List<Inventario> getInventario(@PathVariable Long id) {
		return inventarioRepository.findByRebeldeId(id, null).getContent();
	}
}
