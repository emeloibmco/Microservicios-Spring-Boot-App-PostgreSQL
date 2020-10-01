package com.microservicios.app.transacciones.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.transacciones.services.TransaccionService;
import com.microservicios.commons.transacciones.models.entity.Transaccion;

@RestController
public class TransaccionController {

	@Autowired
	private TransaccionService service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	
	//Ruta variable del ID a buscar
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Transaccion> o = service.findById(id);
		if(o.isEmpty()) {
			//Body JSON vacio
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(o.get());
	}
	
	
	// Se envia la respuesta que se obtiene desde el body de entrada
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Transaccion transaccion){
		Transaccion transaccionDb = service.save(transaccion);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(transaccionDb);
	}	

	@DeleteMapping("({id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
