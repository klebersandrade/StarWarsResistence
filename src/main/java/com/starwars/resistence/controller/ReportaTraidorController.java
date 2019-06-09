package com.starwars.resistence.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.resistence.exception.ResourceNotFoundException;
import com.starwars.resistence.model.Rebelde;
import com.starwars.resistence.model.ReportaTraidor;
import com.starwars.resistence.model.TraidorAdd;
import com.starwars.resistence.repository.RebeldeRepository;
import com.starwars.resistence.repository.ReportaTraidorRepository;

@RestController
public class ReportaTraidorController {
	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private ReportaTraidorRepository reportaTraidorRepository;
	
	@PostMapping("/rebeldes/reporta_traidor")
	public ReportaTraidor addTraidor(@Valid @RequestBody TraidorAdd traidor) {

		if(!rebeldeRepository.existsById(traidor.getDelator())) {
			throw new ResourceNotFoundException("Delator "+ traidor.getDelator() + " não encontrado!");
		}
		
		if(!rebeldeRepository.existsById(traidor.getRebelde())) {
			throw new ResourceNotFoundException("Rebelde Traidor "+ traidor.getRebelde() + " não encontrado!");
		}
		
		Integer ehTraidor = reportaTraidorRepository.findByRebeldeId(traidor.getDelator(), null).getContent().size();		
		if(ehTraidor >= 3) {
			throw new ResourceNotFoundException("O delator "+ traidor.getDelator() + " é um traidor e não pode reportar!");
		}
		
		Long reportado = reportaTraidorRepository.findIfReportou(traidor.getDelator(), traidor.getRebelde());		
		if (reportado  > 0) {
			throw new ResourceNotFoundException("O delator "+ traidor.getDelator() + " já delatou o rebelde "+traidor.getRebelde());
		}			
		
		return rebeldeRepository.findById(traidor.getRebelde()).map(rebelde -> {
			ReportaTraidor t = new ReportaTraidor();
			t.setMotivo(traidor.getMotivo());
			t.setRebelde(rebelde);
			Rebelde delatorReportado = rebeldeRepository.getOne(traidor.getDelator());
			t.setDelator(delatorReportado);
			return reportaTraidorRepository.save(t);
		}).orElseThrow(() -> new ResourceNotFoundException("rebeldeId "+ traidor.getRebelde() + " não encontrado!"));		
	}

}
