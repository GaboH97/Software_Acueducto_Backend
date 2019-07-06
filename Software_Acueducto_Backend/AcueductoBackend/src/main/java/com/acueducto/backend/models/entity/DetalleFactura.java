package com.acueducto.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detalles_factura")
public class DetalleFactura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tarifa_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Tarifa tarifa;
	
	private Double cantidad;
	
	@NotNull
	private Double valor;
	
	public DetalleFactura() {
		// TODO Auto-generated constructor stub
	}
	
	public DetalleFactura(Tarifa tarifa) {
		this.tarifa = tarifa;
		this.valor = tarifa.getValorTarifa();
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Double getImporte() {
		return cantidad.doubleValue()*tarifa.getValorTarifa();
	}

}
