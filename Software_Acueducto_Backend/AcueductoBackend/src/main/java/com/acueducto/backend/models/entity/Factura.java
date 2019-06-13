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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private Integer id;
	
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
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	//@JoinTable(inverseJoinColumns=@JoinColumn(name="suscriptor_cedula"))
	private List<DetalleFactura> detallesFactura;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cedula_suscriptor")
	@JsonBackReference
	private Suscriptor suscriptor;
	
	public Factura() {
		this.detallesFactura= new ArrayList<DetalleFactura>();
	}
	
	@PrePersist
	public void prePersist() {
		//Ejecuta este método justo antes de persistir el objeto
		this.fechaEmision = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.fechaMaximoPago = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(15).toInstant());
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	public List<DetalleFactura> getDetallesFactura() {
		return detallesFactura;
	}
	
	public Suscriptor getSuscriptor() {
		return suscriptor;
	}
	
	public void setDetallesFactura(List<DetalleFactura> detallesFactura) {
		this.detallesFactura = detallesFactura;
	}
	
	public void setSuscriptor(Suscriptor suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	/*
	 * Este campo es calculado y automáticamente se agrega como un campo al momento de consultar
	 */
	public Double getGranTotal() {
		return detallesFactura.stream().mapToDouble(i -> i.getValor()).sum();
	}

}
