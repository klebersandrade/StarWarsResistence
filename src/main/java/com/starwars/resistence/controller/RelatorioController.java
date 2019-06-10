package com.starwars.resistence.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.resistence.exception.ResourceNotFoundException;
import com.starwars.resistence.model.InventarioReport;
import com.starwars.resistence.model.Localizacao;
import com.starwars.resistence.repository.InventarioRepository;
import com.starwars.resistence.repository.LocalizacaoRepository;
import com.starwars.resistence.repository.RebeldeRepository;

@RestController()
@RequestMapping("/relatorios")
public class RelatorioController {
	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private InventarioRepository inventarioRepository;
	@Autowired
	private LocalizacaoRepository localizacaoRepository;
	
	@GetMapping("/porcentagem_traidores")
	public String getPorcTraidores() {
		Double qtd = rebeldeRepository.getPorcTraidores();
		return "Porcentagem de Traidores: "+String.format("%.2f", qtd)+"%";
	}
	
	@GetMapping("/porcentagem_rebeldes")
	public String getPorcRebeldes() {
		Double qtd = rebeldeRepository.getPorcRebeldes();
		return "Porcentagem de Rebeldes: "+String.format("%.2f", qtd)+"%";
	}
	
	
	@GetMapping("/qtd_media_recursos") 
	public String getQtdMediaRecursos() {
		Integer qtdRebeldes = rebeldeRepository.getQtdRebeldesValidos();
		List<InventarioReport> recursos = inventarioRepository.getRecursosRebeldes(); 
		String retorno = "";
		for (int i = 0; i < recursos.size(); i++) { 
			InventarioReport inv = recursos.get(i); 
			Double media = (inv.getQuantidade() * 1.00) / (qtdRebeldes);
			retorno = retorno.concat(inv.getItem().name()+": "+media+" por rebelde; \n");
		} 
		return retorno.trim(); 
	}
	
	@GetMapping("/pontos_perdidos") 
	public String getPontosPerdidos() {		
		List<InventarioReport> recursos = inventarioRepository.getRecursosTraidores(); 
		Integer total = 0;
		for (int i = 0; i < recursos.size(); i++) { 
			InventarioReport inv = recursos.get(i); 
			
			switch (inv.getItem()) {
			case Arma:
				total += 4 * inv.getQuantidade();
				break;
			case Municao:
				total += 3 * inv.getQuantidade();
				break;
			case Agua:
				total += 2 * inv.getQuantidade();
				break;
			case Comida:
				total += 1 * inv.getQuantidade();
				break;
			}				
		} 
		return "Pontos perdidos devido a traidores: "+total; 
	}
	
	@GetMapping("/historico_localizacao/{rebeldeId}") 
	public String getQtdMediaRecursos(@PathVariable(value = "rebeldeId") Long rebeldeId) {
		
		if(!localizacaoRepository.findByRebeldeId(rebeldeId, null).hasContent()) {
			throw new ResourceNotFoundException("Nenhuma localização encontrada!");
		}
		List<Localizacao> local = localizacaoRepository.findByRebeldeId(rebeldeId, null).getContent(); 
		String retorno = "";
		
		String pattern = "dd/MM/yyyy hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		for (int i = 0; i < local.size(); i++) { 
			Localizacao localizacao = local.get(i); 			
			retorno = retorno.concat("Estava na Base \""+localizacao.getNomeBase()+
					"\" às "+simpleDateFormat.format(localizacao.getCriacaoDt())+" - Localização: ["+
					"Latidude="+localizacao.getLatitude()+"/Logintude="+localizacao.getLongetude()+"];\n");
		} 
		return retorno.trim(); 
	}
	 
}
