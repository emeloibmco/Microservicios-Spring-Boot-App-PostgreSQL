package com.microservicios.app.transacciones;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.microservicios.commons.transacciones.models.entity"})
public class MicroserviciosTransaccionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosTransaccionesApplication.class, args);
	}

}
