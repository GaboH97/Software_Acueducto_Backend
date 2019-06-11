package com.acueducto.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class PredioID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int lugarId;
	
	private String numeroMatricula;
	
	public PredioID() {}

	public PredioID(int lugarId, String numeroMatricula) {
		this.lugarId = lugarId;
		this.numeroMatricula = numeroMatricula;
	}

	public int getLugarId() {
		return lugarId;
	}

	public void setLugarId(int lugarId) {
		this.lugarId = lugarId;
	}

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

}
