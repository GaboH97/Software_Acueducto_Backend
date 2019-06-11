package com.acueducto.backend.models.entity;

import java.io.Serializable;

public class AsignacionID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PredioID predio;
	
	private String suscriptor;
	
	
	public AsignacionID() {
	}
	
	public AsignacionID(PredioID predio, String suscriptor) {
		this.predio = predio;
		this.suscriptor = suscriptor;
	}

	public PredioID getPredio() {
		return predio;
	}

	public void setPredio(PredioID predio) {
		this.predio = predio;
	}

	public String getSuscriptor() {
		return suscriptor;
	}

	public void setSuscriptor(String suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	

}
