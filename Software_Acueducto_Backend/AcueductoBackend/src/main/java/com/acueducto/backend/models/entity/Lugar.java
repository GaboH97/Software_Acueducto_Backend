package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "lugares")
public class Lugar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	
	@NotNull
	private String tipo;

	@ManyToOne
	@JsonBackReference
	private Lugar ubicado;
	
	@OneToMany(mappedBy = "ubicado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lugar> lugares;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Lugar getUbicado() {
		return ubicado;
	}

	public void setUbicado(Lugar ubicado) {
		this.ubicado = ubicado;
	}
	
	public List<Lugar> getLugares() {
		return lugares;
	}
	
	public void setLugares(List<Lugar> lugares) {
		this.lugares = lugares;
	}

}
