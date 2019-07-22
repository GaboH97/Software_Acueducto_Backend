package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	@ManyToOne
	@JoinColumn(name = "lugar_id")
	private Lugar vereda;

	@NotNull
	@Column(unique = true)
	private String nombre;

	@NotNull
	private Integer estrato;

	private Double latitud;
	private Double longitud;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Suscriptor suscriptor;

	@OneToMany(mappedBy = "predio", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("predio")
	private List<Factura> facturas;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "predio_lugar_id", referencedColumnName = "lugar_id"),
			@JoinColumn(name = "predio_numero_matricula", referencedColumnName = "numero_matricula") })
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDireccion(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEstrato() {
		return estrato;
	}

	public void setEstrato(Integer estrato) {
		this.estrato = estrato;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
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

	public void setVereda(Lugar vereda) {
		this.vereda = vereda;
	}

	public Lugar getVereda() {
		return vereda;
	}
	
	public boolean hasFacturas() {
		return !this.facturas.isEmpty();
	}
	
	public boolean estaAlDia() {
		return this.facturas.stream().allMatch(f-> f.getEstadoFactura().equals("PA"));
	}
	
	public Double obtenerDeudaTotal() {
		return this.facturas.stream().filter(f-> !f.getEstadoFactura().equals("PA"))
				.mapToDouble(i-> i.getGranTotal()).sum();
	}

//	public String getEstadoPredio() {
//		return facturas.stream().allMatch(f -> !f.getEstadoFactura().equals("VE")) ? "D" : "M";
//	}

}
