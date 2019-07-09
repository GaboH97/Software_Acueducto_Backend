package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "historial_predio")
public class HistorialPredio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_inicio")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_final")
	private Date fechaFinal;
	
	@OneToOne
	private Suscriptor suscriptor;
	
	public HistorialPredio() {}
	
	
	@PrePersist
	public void prePersist() {
		this.fechaInicio = Date.from(LocalDate.now().atStartOfDay()
			      .atZone(ZoneId.systemDefault())
			      .toInstant());
	}
	
	@PreUpdate
	public void preUpdate() {
		this.fechaFinal = Date.from(LocalDate.now().atStartOfDay()
			      .atZone(ZoneId.systemDefault())
			      .toInstant());
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
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
	
}
