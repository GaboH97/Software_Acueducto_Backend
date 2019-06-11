package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "predios")
@IdClass(PredioID.class)
public class Predio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="numero_matricula")
	private String numeroMatricula;
	
	@Id
	@ManyToOne
	@JoinColumn(name="lugar_id", referencedColumnName = "id")
	private Lugar lugarId;
	
	private String direccion;
	
	private int estrato;
	
	private double latitud;
	
	private double longitud;
	
	
	@OneToMany(mappedBy="predio",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//@JoinTable(inverseJoinColumns=@JoinColumn(name="suscriptor_cedula"))
	private List<Asignacion> asignaciones;

	public String getNumeroMatricula() {
		return numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getEstrato() {
		return estrato;
	}

	public void setEstrato(int estrato) {
		this.estrato = estrato;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

}
