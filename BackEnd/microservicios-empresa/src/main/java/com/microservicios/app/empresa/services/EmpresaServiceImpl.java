package com.microservicios.app.empresa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.empresa.models.repository.EmpresaRepository;
import com.microservicios.commons.transacciones.models.entity.Empresa;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Empresa> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Empresa> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Empresa save(Empresa empresa) {
		return repository.save(empresa);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}


}
