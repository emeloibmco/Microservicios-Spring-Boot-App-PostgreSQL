package com.microservicios.commons.transacciones.models.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="empresas")
public class Empresa {

	@Id
	@Column(name="nit")
	private Long idEmpresa;
	
	@Column(name="nombre_empresa")
	private String nombreEmpresa;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy ="persona" )
	private Set<Transaccion> transacciones;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	
	public Long getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}


	public String getNombreEmpresa() {
		return nombreEmpresa;
	}


	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}


	public Set<Transaccion> getTransacciones() {
		return transacciones;
	}


	public void setTransacciones(Set<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
	
}
