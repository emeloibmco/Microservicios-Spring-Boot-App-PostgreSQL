package com.microservicios.app.transacciones.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.transacciones.models.repository.EmpresaRepository;
import com.microservicios.app.transacciones.models.repository.PersonaRepository;
import com.microservicios.app.transacciones.models.repository.TransaccionRepository;
import com.microservicios.commons.transacciones.models.entity.Empresa;
import com.microservicios.commons.transacciones.models.entity.Persona;
import com.microservicios.commons.transacciones.models.entity.Transaccion;


@Service
public class TransaccionServiceImpl implements TransaccionService {

	@Autowired
	private TransaccionRepository repository;
	
	@Autowired
	private PersonaRepository personaRep;
	
	@Autowired
	private EmpresaRepository empresaRep; 
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Transaccion> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Transaccion> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Transaccion save(Transaccion transaccion) {
		long business_id=transaccion.getEmpresa().getIdEmpresa();
		long client_id=transaccion.getPersona().getIdPersona();
		Optional<Empresa> business = empresaRep.findById(business_id);
		Optional<Persona> client = personaRep.findById(client_id);
		Set<Transaccion> invoices = new HashSet<>();
		invoices.add(transaccion);
		if(business.isPresent()) {
			business.get().setTransacciones(invoices);
			transaccion.setEmpresa(business.get());
		}
		if(client.isPresent()) {
			client.get().setTransacciones(invoices);
			transaccion.setPersona(client.get());
		}
		
		return repository.save(transaccion);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
