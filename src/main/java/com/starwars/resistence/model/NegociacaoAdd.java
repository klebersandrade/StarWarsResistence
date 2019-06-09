package com.starwars.resistence.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class NegociacaoAdd {
	@NotNull
	private Long comprador;
	@NotNull
	private Long vendedor;
	@NotNull
	private List<NegociacaoItemAdd> itensComprador;
	@NotNull
	private List<NegociacaoItemAdd> itensVendedor;
	public Long getComprador() {
		return comprador;
	}
	public void setComprador(Long comprador) {
		this.comprador = comprador;
	}
	public Long getVendedor() {
		return vendedor;
	}
	public void setVendedor(Long vendedor) {
		this.vendedor = vendedor;
	}
	public List<NegociacaoItemAdd> getItensComprador() {
		return itensComprador;
	}
	public void setItensComprador(List<NegociacaoItemAdd> itensComprador) {
		this.itensComprador = itensComprador;
	}
	public List<NegociacaoItemAdd> getItensVendedor() {
		return itensVendedor;
	}
	public void setItensVendedor(List<NegociacaoItemAdd> itensVendedor) {
		this.itensVendedor = itensVendedor;
	}	

}
