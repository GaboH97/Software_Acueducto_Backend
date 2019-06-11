package com.acueducto.backend.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.acueducto.backend.models.entity.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "suscriptores")
public class Suscriptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String cedula;
		
	@NotNull
	@Size(max = 10)
	private String nombre;
	
	@NotNull
	private String apellido;
	
	@NotNull
	private String estado;

	@NotNull
	@Column(name = "estado_cuenta")
	private String estadoCuenta;

	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;
	
	@NotNull
	private String genero;
	
	@NotNull
	@Column(name="numero_telefono")
	private String numeroTelefono;
	
	@Column(name="correo_electronico")
	private String correoElectronico;
	
	@OneToMany(mappedBy="suscriptor",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//@JoinTable(inverseJoinColumns=@JoinColumn(name="suscriptor_cedula"))
	private List<Asignacion> asignaciones;
	
	

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	
}
