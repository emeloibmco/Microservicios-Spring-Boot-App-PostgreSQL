package com.microservicios.app.empresa.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.microservicios.commons.transacciones.models.entity.Empresa;



public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

}
