package com.microservicios.app.transacciones.services;

import java.util.Optional;

import com.microservicios.commons.transacciones.models.entity.Transaccion;

public interface TransaccionService {
	
	public Iterable<Transaccion> findAll();
	
	public Optional<Transaccion> findById(Long id);
	
	public Transaccion save(Transaccion transaccion);
	
	public void deleteById(Long id);

}
