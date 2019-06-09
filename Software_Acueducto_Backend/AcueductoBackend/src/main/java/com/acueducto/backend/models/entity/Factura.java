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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name = "fecha_emision")
	private Date fechaEmision;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name = "periodo_facturado")
	private Date periodofacturado;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name = "fecha_maximo_pago")
	private Date fechaMaximoPago;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name = "fecha_pago")
	private Date fechaPago;
	
	@OneToMany(mappedBy="factura",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//@JoinTable(inverseJoinColumns=@JoinColumn(name="suscriptor_cedula"))
	private List<DetalleFactura> detallesFactura;
	
	
	@PrePersist
	public void prePersist() {
		//Ejecuta este método justo antes de persistir el objeto
		fechaEmision = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getPeriodofacturado() {
		return periodofacturado;
	}

	public void setPeriodofacturado(Date periodofacturado) {
		this.periodofacturado = periodofacturado;
	}

	public Date getFechaMaximoPago() {
		return fechaMaximoPago;
	}

	public void setFechaMaximoPago(Date fechaMaximoPago) {
		this.fechaMaximoPago = fechaMaximoPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	

}
