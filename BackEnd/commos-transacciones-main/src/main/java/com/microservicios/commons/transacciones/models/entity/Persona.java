package com.microservicios.commons.transacciones.models.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="personas")
public class Persona {
	
	@Id
	@Column(name="id_persona")
	private Long idPersona;
	
	private String nombre; 
	
	@Column(name="tipo_doc")
	private String tipoDoc; 
	private Long celular;
	private String correo;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy ="persona" )
	private Set<Transaccion> transacciones;
	
	
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public Set<Transaccion> getTransacciones() {
		return transacciones;
	}
	public void setTransacciones(Set<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getCelular() {
		return celular;
	}
	public void setCelular(Long celular) {
		this.celular = celular;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
