package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tarifas")
public class Tarifa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name="fecha_inicio")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name="fecha_final")
	private Date fechaFinal;
	
	@NotNull
	private double valorTarifa;
	
	@NotNull
	private String descripcion;
	
	@OneToMany(mappedBy="tarifa",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//@JoinTable(inverseJoinColumns=@JoinColumn(name="suscriptor_cedula"))
	private List<DetalleFactura> detallesFactura;
	
	
	public Tarifa() {
	
	}
	
	@PrePersist
	public void prePersist() {
		//Ejecuta este método justo antes de persistir el objeto
		System.out.println("AQUI ENTRA");
		fechaInicio = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	

}
