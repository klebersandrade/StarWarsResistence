package com.starwars.resistence.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "localizacoes")
public class Localizacao extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Double latitude;
	@NotNull
	private Double longetude;
	@NotNull
	@Size(max = 100	)
	private String nomeBase;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rebelde_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Rebelde rebelde;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongetude() {
		return longetude;
	}
	public void setLongetude(Double longetude) {
		this.longetude = longetude;
	}
	public String getNomeBase() {
		return nomeBase;
	}
	public void setNomeBase(String nomeBase) {
		this.nomeBase = nomeBase;
	}
	public Rebelde getRebelde() {
		return rebelde;
	}
	public void setRebelde(Rebelde rebelde) {
		this.rebelde = rebelde;
	}

}
