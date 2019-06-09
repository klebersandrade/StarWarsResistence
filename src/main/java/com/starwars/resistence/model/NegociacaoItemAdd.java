package com.starwars.resistence.model;

import javax.validation.constraints.NotNull;

import com.starwars.resistence.enumeration.Item;

public class NegociacaoItemAdd {
	@NotNull
	private Item item;
	@NotNull
	private Integer quantidade;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}	
}
