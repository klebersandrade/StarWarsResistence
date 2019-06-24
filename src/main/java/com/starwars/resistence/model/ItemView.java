package com.starwars.resistence.model;

import com.starwars.resistence.enumeration.Item;

public class ItemView {
	private Item item;
	private Double quantidade;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

}
