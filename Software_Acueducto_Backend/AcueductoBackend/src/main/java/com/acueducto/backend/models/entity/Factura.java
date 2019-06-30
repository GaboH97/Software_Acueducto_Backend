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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * DOMINIO DE VALORES PARA ESTADO DE FACTURA
 * 		
 * 		PP => Por Pagar
 * 		VE => Vencida
 * 		PA => Pagada
 *
 */
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_emision")
	private Date fechaEmision;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "periodo_facturado")
	private Date periodofacturado;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_maximo_pago")
	private Date fechaMaximoPago;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_pago")
	private Date fechaPago;
	
	@Column(name="estado_factura")
	private String estadoFactura;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<DetalleFactura> detallesFactura;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Predio predio;
	
	public Factura() {
		this.detallesFactura= new ArrayList<DetalleFactura>();
	}
	
	@PrePersist
	public void prePersist() {
		//Ejecuta este método justo antes de persistir el objeto
		this.fechaEmision = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.fechaMaximoPago = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(15).toInstant());
		this.estadoFactura = "PP";
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
	
	public void setDetallesFactura(List<DetalleFactura> detallesFactura) {
		this.detallesFactura = detallesFactura;
	}
	
	public Predio getPredio() {
		return predio;
	}
	
	public void setPredio(Predio predio) {
		this.predio = predio;
	}
	
	/*
	 * Este campo es calculado y automáticamente se serializa como un campo al momento de consultar
	 */
	public Double getGranTotal() {
		return detallesFactura.stream().mapToDouble(i -> i.getImporte()).sum();
	}

}
