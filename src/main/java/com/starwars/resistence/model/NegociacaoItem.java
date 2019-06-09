package com.starwars.resistence.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starwars.resistence.enumeration.Item;

@Entity
@Table(name = "negociacao_itens")
public class NegociacaoItem extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negociacao_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Negociacao negociacao;
	private Item item;
	private Integer quantidade;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negociador_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Rebelde negociador;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Negociacao getNegociacao() {
		return negociacao;
	}
	public void setNegociacao(Negociacao negociacao) {
		this.negociacao = negociacao;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Rebelde getNegociador() {
		return negociador;
	}
	public void setNegociador(Rebelde negociador) {
		this.negociador = negociador;
	}

}
