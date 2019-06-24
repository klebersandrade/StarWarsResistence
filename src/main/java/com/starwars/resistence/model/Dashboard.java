package com.starwars.resistence.model;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
	private Integer qtdCadastrado;
	private Integer qtdTraidores;
	private Integer qtdRebeldes;
	private Double porcTraidores;
	private Double porcRebeldes;
	private Integer pontosPerdidos;
	private List<ItemView> recursos;
	
	public Dashboard() {
		recursos = new ArrayList<ItemView>();
	}
	
	public Integer getQtdCadastrado() {
		return qtdCadastrado;
	}
	public void setQtdCadastrado(Integer qtdCadastrado) {
		this.qtdCadastrado = qtdCadastrado;
	}
	public Integer getQtdTraidores() {
		return qtdTraidores;
	}
	public void setQtdTraidores(Integer qtdTraidores) {
		this.qtdTraidores = qtdTraidores;
	}
	public Integer getQtdRebeldes() {
		return qtdRebeldes;
	}
	public void setQtdRebeldes(Integer qtdRebeldes) {
		this.qtdRebeldes = qtdRebeldes;
	}
	public Double getPorcTraidores() {
		return porcTraidores;
	}
	public void setPorcTraidores(Double porcTraidores) {
		this.porcTraidores = porcTraidores;
	}
	public Double getPorcRebeldes() {
		return porcRebeldes;
	}
	public void setPorcRebeldes(Double porcRebeldes) {
		this.porcRebeldes = porcRebeldes;
	}
	public Integer getPontosPerdidos() {
		return pontosPerdidos;
	}
	public void setPontosPerdidos(Integer pontosPerdidos) {
		this.pontosPerdidos = pontosPerdidos;
	}
	public List<ItemView> getRecursos() {
		return recursos;
	}
	public void setRecursos(List<ItemView> recursos) {
		this.recursos = recursos;
	}
}
