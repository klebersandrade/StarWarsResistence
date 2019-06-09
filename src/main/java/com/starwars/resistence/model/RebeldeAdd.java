package com.starwars.resistence.model;

import java.util.List;

public class RebeldeAdd {
	private Rebelde rebelde;
	private List<Inventario> inventario;
	
	public Rebelde getRebelde() {
		return rebelde;
	}
	public void setRebelde(Rebelde rebelde) {
		this.rebelde = rebelde;
	}
	public List<Inventario> getInventario() {
		return inventario;
	}
	public void setInventario(List<Inventario> inventario) {
		this.inventario = inventario;
	}
}
