package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "predios")
//@IdClass(PredioID.class)
public class Predio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "numero_matricula")
	private String numeroMatricula;

	@Id
	@ManyToOne
	private Lugar lugar;

	@NotNull
	private String nombre;

	@NotNull
	private int estrato;

	private double latitud;
	private double longitud;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@NotNull
	private Suscriptor suscriptor;

	@OneToMany(mappedBy = "predio", fetch = FetchType.LAZY, orphanRemoval = false)
	private List<Factura> facturas;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "predio_lugar_id", referencedColumnName = "lugar_id"),
			@JoinColumn(name = "predio_numero_matricula", referencedColumnName = "numero_matricula") })
	private List<HistorialPredio> historialPredio;

	public Predio() {
		historialPredio = new ArrayList<>();
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setDireccion(String nombre) {
		this.nombre = nombre;
	}

	public int getEstrato() {
		return estrato;
	}

	public void setEstrato(int estrato) {
		this.estrato = estrato;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Suscriptor getSuscriptor() {
		return suscriptor;
	}

	public void setSuscriptor(Suscriptor suscriptor) {
		this.suscriptor = suscriptor;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public List<HistorialPredio> getHistorialPredio() {
		return historialPredio;
	}

	public void setHistorialPredio(List<HistorialPredio> historialPredio) {
		this.historialPredio = historialPredio;
	}
	
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}
	
	public Lugar getLugar() {
		return lugar;
	}
}
