package com.microservicios.app.persona.services;

import java.util.Optional;

import com.microservicios.commons.transacciones.models.entity.Persona;



public interface PersonaService {

	public Iterable<Persona> findAll();
	
	public Optional<Persona> findById(Long id);
	
	public Persona save(Persona persona);
	
	public void deleteById(Long id);
}
