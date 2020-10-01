package com.microservicios.app.persona.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.persona.models.repository.PersonaRepository;
import com.microservicios.commons.transacciones.models.entity.Persona;

@Service
public class PersonaServiceImpl implements PersonaService{

	@Autowired
	private PersonaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Persona> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Persona> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Persona save(Persona persona) {
		return repository.save(persona);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}


}
