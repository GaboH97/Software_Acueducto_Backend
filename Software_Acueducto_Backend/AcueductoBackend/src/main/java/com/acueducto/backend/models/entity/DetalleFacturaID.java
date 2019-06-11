package com.acueducto.backend.models.entity;

import java.io.Serializable;

public class DetalleFacturaID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int factura;
	
	private int tarifa;
	
	public DetalleFacturaID() {
		// TODO Auto-generated constructor stub
	}

	public DetalleFacturaID(int facturaId, int tarifaId) {
		super();
		this.factura = facturaId;
		this.tarifa = tarifaId;
	}

	public int getFacturaId() {
		return factura;
	}

	public void setFacturaId(int facturaId) {
		this.factura = facturaId;
	}

	public int getTarifaId() {
		return tarifa;
	}

	public void setTarifaId(int tarifaId) {
		this.tarifa = tarifaId;
	}
	
}
