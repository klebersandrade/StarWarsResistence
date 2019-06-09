package com.starwars.resistence.model;

import javax.validation.constraints.NotNull;

public class TraidorAdd {
	@NotNull
	private Long delator;
	@NotNull
	private Long rebelde;
	private String motivo;
	public Long getDelator() {
		return delator;
	}
	public void setDelator(Long delator) {
		this.delator = delator;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Long getRebelde() {
		return rebelde;
	}
	public void setRebelde(Long rebelde) {
		this.rebelde = rebelde;
	}

}
