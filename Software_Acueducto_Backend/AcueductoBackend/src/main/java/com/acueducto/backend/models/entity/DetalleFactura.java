package com.acueducto.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "detalles_factura")
@IdClass(DetalleFacturaID.class)
public class DetalleFactura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="factura_id", referencedColumnName = "id")
	private Factura factura;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tarifa_id", referencedColumnName = "id")
	private Tarifa tarifa;
	
	@NotNull
	@Column(name = "consumo_actual")
	private double consumoActual;
	
	@NotNull
	private double valor;

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public double getConsumoActual() {
		return consumoActual;
	}

	public void setConsumoActual(double consumoActual) {
		this.consumoActual = consumoActual;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}	

}
