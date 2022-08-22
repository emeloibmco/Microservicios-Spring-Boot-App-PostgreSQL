package com.microservicios.app.transacciones.models.repository;

import org.springframework.data.repository.CrudRepository;

//import com.microservicios.commons.transacciones.models.entity.Transaccion;
import com.microservicios.app.transacciones.entity.Transaccion;
public interface TransaccionRepository extends CrudRepository<Transaccion, Long> {

}
