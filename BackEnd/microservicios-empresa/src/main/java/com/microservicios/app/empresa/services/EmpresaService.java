package com.microservicios.app.empresa.services;

import java.util.Optional;

import com.microservicios.commons.transacciones.models.entity.Empresa;


public interface EmpresaService {
	
	public Iterable<Empresa> findAll();
	
	public Optional<Empresa> findById(Long id);
	
	public Empresa save(Empresa empresa);
	
	public void deleteById(Long id);
}
