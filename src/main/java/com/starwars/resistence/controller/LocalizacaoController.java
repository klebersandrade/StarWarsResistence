package com.starwars.resistence.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.resistence.exception.ResourceNotFoundException;
import com.starwars.resistence.model.Inventario;
import com.starwars.resistence.model.Localizacao;
import com.starwars.resistence.repository.LocalizacaoRepository;
import com.starwars.resistence.repository.RebeldeRepository;
import com.starwars.resistence.repository.ReportaTraidorRepository;

@RestController
public class LocalizacaoController {
	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private LocalizacaoRepository localizacaoRepository;	
	@Autowired
	private ReportaTraidorRepository reportaTraidorRepository;
	
	@PostMapping("/rebeldes/{rebeldeId}/localizacoes")
	public Localizacao addLocalizacao(@PathVariable(value = "rebeldeId") Long rebeldeId, @Valid @RequestBody Localizacao localizacao) {
		return rebeldeRepository.findById(rebeldeId).map(rebelde -> {
			if(reportaTraidorRepository.findByRebeldeId(rebeldeId, null).getContent().size() >= 3)
				throw new ResourceNotFoundException("O Rebelde "+ rebeldeId + " é um traidor e não pode informar localização!");
			localizacao.setRebelde(rebelde);
			return localizacaoRepository.save(localizacao);
		}).orElseThrow(() -> new ResourceNotFoundException("rebeldeId "+ rebeldeId + " não encontrado!"));		
	}
	
	@GetMapping("/rebeldes/localizacoes/{id}")
	public List<Localizacao> getLocalizacoes(@PathVariable Long id) {
		return localizacaoRepository.findByRebeldeId(id, null).getContent();
	}

}
