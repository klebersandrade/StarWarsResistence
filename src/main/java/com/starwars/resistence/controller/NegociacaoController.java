package com.starwars.resistence.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.resistence.exception.ResourceNotFoundException;
import com.starwars.resistence.model.Inventario;
import com.starwars.resistence.model.Negociacao;
import com.starwars.resistence.model.NegociacaoAdd;
import com.starwars.resistence.model.NegociacaoItem;
import com.starwars.resistence.model.NegociacaoItemAdd;
import com.starwars.resistence.model.ReportaTraidor;
import com.starwars.resistence.repository.InventarioRepository;
import com.starwars.resistence.repository.NegociacaoItemRepository;
import com.starwars.resistence.repository.NegociacaoRepository;
import com.starwars.resistence.repository.RebeldeRepository;
import com.starwars.resistence.repository.ReportaTraidorRepository;

@RestController
public class NegociacaoController {
	@Autowired
	private RebeldeRepository rebeldeRepository;
	@Autowired
	private NegociacaoRepository negociacaoRepository;	
	@Autowired
	private NegociacaoItemRepository negociacaoItemRepository;
	@Autowired
	private ReportaTraidorRepository reportaTraidorRepository;
	@Autowired
	private InventarioRepository inventarioRepository;
	
	@PostMapping("/rebeldes/negociacoes")
	public Negociacao addNegociacao(@Valid @RequestBody NegociacaoAdd negociacaoAdd) {		
		if(!rebeldeRepository.existsById(negociacaoAdd.getComprador()))
			throw new ResourceNotFoundException("Rebelde "+negociacaoAdd.getComprador()+" não encontrado!");
		if(!rebeldeRepository.existsById(negociacaoAdd.getVendedor()))
			throw new ResourceNotFoundException("Rebelde "+negociacaoAdd.getVendedor()+" não encontrado!");
		
		List<ReportaTraidor> rt = reportaTraidorRepository.findByRebeldeId(negociacaoAdd.getComprador(), null).getContent();
		
		if(rt != null && rt.size() >= 3) {
			throw new ResourceNotFoundException("O Rebelde "+negociacaoAdd.getComprador()+" é um traidor e não pode fazer negociação!");
		}
		
		rt = reportaTraidorRepository.findByRebeldeId(negociacaoAdd.getVendedor(), null).getContent();
		
		if(rt != null && rt.size() >= 3) {
			throw new ResourceNotFoundException("O Rebelde "+negociacaoAdd.getVendedor()+" é um traidor e não pode fazer negociação!");
		}
		if(!checkItensOk(negociacaoAdd.getItensComprador(), negociacaoAdd.getComprador()))
			throw new ResourceNotFoundException("Algum(s) item(ns) do rebelde "+negociacaoAdd.getComprador()+" não confere com o que está informado no inventário atual!");
		if(!checkItensOk(negociacaoAdd.getItensVendedor(), negociacaoAdd.getVendedor()))
			throw new ResourceNotFoundException("Algum(s) item(ns) do rebelde "+negociacaoAdd.getVendedor()+" não confere com o que está informado no inventário atual!");
		if(!conferenciaItens(negociacaoAdd)) {
			throw new ResourceNotFoundException("A soma dos pontos dos itens não conferem!");
		}
		
		atualizaItensRebelde(negociacaoAdd.getItensComprador(), negociacaoAdd.getItensVendedor(), negociacaoAdd.getVendedor());
		atualizaItensRebelde(negociacaoAdd.getItensVendedor(), negociacaoAdd.getItensComprador(), negociacaoAdd.getComprador());
		
		Negociacao n = new Negociacao();
		n.setComprador(rebeldeRepository.getOne(negociacaoAdd.getComprador()));
		n.setVendedor(rebeldeRepository.getOne(negociacaoAdd.getVendedor()));
		n = negociacaoRepository.save(n);
		
		for (int i = 0; i < negociacaoAdd.getItensComprador().size(); i++) {
			NegociacaoItemAdd ite = negociacaoAdd.getItensComprador().get(i); 
			NegociacaoItem item = new NegociacaoItem();
			item.setItem(ite.getItem());
			item.setNegociacao(n);
			item.setNegociador(rebeldeRepository.getOne(negociacaoAdd.getComprador()));
			item.setQuantidade(ite.getQuantidade());
			
			negociacaoItemRepository.save(item);
		}
		
		for (int i = 0; i < negociacaoAdd.getItensVendedor().size(); i++) {
			NegociacaoItemAdd ite = negociacaoAdd.getItensVendedor().get(i); 
			NegociacaoItem item = new NegociacaoItem();
			item.setItem(ite.getItem());
			item.setNegociacao(n);
			item.setNegociador(rebeldeRepository.getOne(negociacaoAdd.getVendedor()));
			item.setQuantidade(ite.getQuantidade());
			
			negociacaoItemRepository.save(item);
		}
		return n;
	}
	
	private Boolean conferenciaItens(NegociacaoAdd negociacao) {
		Integer totalComprador = 0;
		Integer totalVendedor = 0;
		
		for (int i = 0; i <negociacao.getItensComprador().size(); i++) {
			NegociacaoItemAdd item = negociacao.getItensComprador().get(i);
			
			switch (item.getItem()) {
			case Arma:
				totalComprador += 4 * item.getQuantidade();
				break;
			case Municao:
				totalComprador += 3 * item.getQuantidade();
				break;
			case Agua:
				totalComprador += 2 * item.getQuantidade();
				break;
			case Comida:
				totalComprador += 1 * item.getQuantidade();
				break;
			}
		}
		
		for (int i = 0; i <negociacao.getItensVendedor().size(); i++) {
			NegociacaoItemAdd item = negociacao.getItensVendedor().get(i);
			
			switch (item.getItem()) {
			case Arma:
				totalVendedor += 4 * item.getQuantidade();
				break;
			case Municao:
				totalVendedor += 3 * item.getQuantidade();
				break;
			case Agua:
				totalVendedor += 2 * item.getQuantidade();
				break;
			case Comida:
				totalVendedor += 1 * item.getQuantidade();
				break;
			}
		}
		
		return (totalComprador == totalVendedor);
	}
	
	private Boolean checkItensOk(List<NegociacaoItemAdd> itens, Long rebelde) {				
		for (int i = 0; i < itens.size(); i++) {
			NegociacaoItemAdd ite = itens.get(i);
			if(!inventarioRepository.findByItemAndRebeldeId(ite.getItem(), rebelde).isPresent())
				return false;
			Inventario inv = inventarioRepository.findByItemAndRebeldeId(ite.getItem(), rebelde).get();
			if(inv.getQuantidade() < ite.getQuantidade())
				return false;
		}
		
		return true;
	}
	
	private void atualizaItensRebelde(List<NegociacaoItemAdd> itensCompra, List<NegociacaoItemAdd> itensVenda, Long rebelde) {
		for (int i = 0; i < itensCompra.size(); i++) {
			NegociacaoItemAdd ite = itensCompra.get(i);
			Inventario inv;
			if(inventarioRepository.findByItemAndRebeldeId(ite.getItem(),rebelde).isPresent()) {
				inv = inventarioRepository.findByItemAndRebeldeId(ite.getItem(),rebelde).get();
				inv.setQuantidade(inv.getQuantidade() + ite.getQuantidade());
				inventarioRepository.save(inv);
			} else {
				inv = new Inventario();
				inv.setItem(ite.getItem());
				inv.setQuantidade(ite.getQuantidade());
				inv.setRebelde(rebeldeRepository.getOne(rebelde));
				inventarioRepository.save(inv);
			}			
		}
		for (int i = 0; i < itensVenda.size(); i++) {
			NegociacaoItemAdd ite = itensVenda.get(i);
			Inventario inv = inventarioRepository.findByItemAndRebeldeId(ite.getItem(),rebelde).get();
			inv.setQuantidade(inv.getQuantidade() - ite.getQuantidade());
			inventarioRepository.save(inv);
		}
	}
}
