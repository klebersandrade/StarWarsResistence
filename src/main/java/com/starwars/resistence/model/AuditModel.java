package com.starwars.resistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
		value = {"CriacaoDt", "AlteracaoDt"},
		allowGetters = true
)
public abstract class AuditModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "criacao_dt", nullable = false, updatable = false)
	@CreatedDate
	private Date criacaoDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "alteracao_dt", nullable = false)
	@LastModifiedDate
	private Date alteracaoDt;
	
	public Date getCriacaoDt() {
		return criacaoDt;
	}
	public void setCriacaoDt(Date criacaoDt) {
		this.criacaoDt = criacaoDt;
	}
	public Date getAlteracaoDt() {
		return alteracaoDt;
	}
	public void setAlteracaoDt(Date alteracaoDt) {
		this.alteracaoDt = alteracaoDt;
	}

}
