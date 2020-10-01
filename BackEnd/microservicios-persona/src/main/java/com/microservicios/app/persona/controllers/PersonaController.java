package com.microservicios.app.persona.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.persona.services.PersonaService;
import com.microservicios.commons.transacciones.models.entity.Persona;



@RestController
public class PersonaController {
	
	@Autowired
	private PersonaService service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	//Ruta variable del ID a buscar
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Persona> o = service.findById(id);
		if(o.isEmpty()) {
			//Body JSON vacio
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(o.get());
	}
	
	// Se envia la respuesta que se obtiene desde el body de entrada
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Persona persona){
		Persona personaDb = service.save(persona);
		return ResponseEntity.status(HttpStatus.CREATED).body(personaDb);
	}
	

	@DeleteMapping("({id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Persona persona, @PathVariable Long id){
		Optional<Persona> o = service.findById(id);
		if(o.isEmpty()) {
			//Body JSON vacio
			return ResponseEntity.notFound().build();
		}
		
		Persona personaDb = o.get();
		personaDb.setNombre(persona.getNombre());
		personaDb.setCelular(persona.getCelular());
		personaDb.setCorreo(persona.getCorreo());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(personaDb));
		
	}
	
}
