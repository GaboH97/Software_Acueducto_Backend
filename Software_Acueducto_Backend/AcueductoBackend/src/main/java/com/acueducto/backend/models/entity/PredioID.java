package com.acueducto.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class PredioID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int lugar;
	
	private String numeroMatricula;
	
	public PredioID() {}

	public PredioID(int lugarId, String numeroMatricula) {
		this.lugar = lugarId;
		this.numeroMatricula = numeroMatricula;
	}

	public int getLugarId() {
		return lugar;
	}

	public void setLugarId(int lugarId) {
		this.lugar = lugarId;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

}
