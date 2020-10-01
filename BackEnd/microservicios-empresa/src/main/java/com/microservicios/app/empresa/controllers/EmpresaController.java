package com.microservicios.app.empresa.controllers;

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

import com.microservicios.app.empresa.services.EmpresaService;
import com.microservicios.commons.transacciones.models.entity.Empresa;



@RestController
public class EmpresaController {
	@Autowired
	private EmpresaService service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	//Ruta variable del ID a buscar
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Empresa> o = service.findById(id);
		if(o.isEmpty()) {
			//Body JSON vacio
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(o.get());
	}
	
	// Se envia la respuesta que se obtiene desde el body de entrada
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Empresa empresa){
		Empresa empresaDb = service.save(empresa);
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaDb);
	}
	

	@DeleteMapping("({id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Empresa empresa, @PathVariable Long nit){
		Optional<Empresa> o = service.findById(nit);
		if(o.isEmpty()) {
			//Body JSON vacio
			return ResponseEntity.notFound().build();
		}
		
		Empresa empresaDb = o.get();
		empresaDb.setIdEmpresa(empresa.getIdEmpresa());
		empresaDb.setNombreEmpresa(empresa.getNombreEmpresa());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(empresaDb));
		
	}
}
