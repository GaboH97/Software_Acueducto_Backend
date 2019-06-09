package com.acueducto.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalles_factura")
public class DetalleFactura implements Serializable{
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private Factura factura;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private Tarifa tarifa;
	
	
	@Column(name = "consumo_actual")
	private double consumoActual;
	
	private double valor;
	
	
}
