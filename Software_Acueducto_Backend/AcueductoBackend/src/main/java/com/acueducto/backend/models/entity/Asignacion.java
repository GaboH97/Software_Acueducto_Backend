package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asignaciones")
@IdClass(AsignacionID.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
public class Asignacion implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name = "fecha_inicial")
	private Date fechaInicial;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name = "fecha_final")
	private Date fechaFinal;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cedula_suscriptor", referencedColumnName = "cedula")
	private Suscriptor suscriptor;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="predio_matricula", referencedColumnName = "numero_matricula"),
		@JoinColumn(name="lugar_id", referencedColumnName = "lugar_id"),
	})
	private Predio predio;

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Suscriptor getSuscriptor() {
		return suscriptor;
	}

	public void setSuscriptor(Suscriptor suscriptor) {
		this.suscriptor = suscriptor;
	}

	public Predio getPredio() {
		return predio;
	}

	public void setPredio(Predio predio) {
		this.predio = predio;
	}
	
	
}
