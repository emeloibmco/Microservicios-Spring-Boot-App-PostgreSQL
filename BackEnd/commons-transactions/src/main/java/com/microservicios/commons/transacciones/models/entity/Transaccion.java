package com.microservicios.commons.transacciones.models.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="transacciones")
public class Transaccion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num_factura")
	private Long idFactura;
	
	
	private int	valor;
	
	private String descripcion;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_empresa", referencedColumnName = "nit")
	private Empresa empresa;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
	private Persona persona;

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	
	

	public Long getIdFactura() {
		return idFactura;
	}




	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}




	public Empresa getEmpresa() {
		return empresa;
	}




	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}




	public Persona getPersona() {
		return persona;
	}




	public void setPersona(Persona persona) {
		this.persona = persona;
	}




	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
	

}
