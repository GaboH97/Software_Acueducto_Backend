package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tarifas")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Tarifa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@NotNull
	@Column(name ="valor_tarifa")
	private double valorTarifa;
	
	@NotNull
	private String descripcion;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "tarifa_id")
	private List<HistorialTarifa> historialTarifa;
	
	
	public Tarifa() {
		this.historialTarifa = new ArrayList<HistorialTarifa>();
	}
	
	@PrePersist
	public void prePersist() {
		HistorialTarifa historialTarifa = new HistorialTarifa();
		this.historialTarifa.add(historialTarifa);
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public List<HistorialTarifa> getHistorialTarifa() {
		return historialTarifa;
	}
	
	public void setHistorialTarifa(List<HistorialTarifa> historialTarifa) {
		this.historialTarifa = historialTarifa;
	}
}
