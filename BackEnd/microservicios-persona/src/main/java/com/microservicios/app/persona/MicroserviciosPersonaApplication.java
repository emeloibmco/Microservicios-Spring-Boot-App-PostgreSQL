package com.microservicios.app.persona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.microservicios.commons.transacciones.models.entity"})
public class MicroserviciosPersonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosPersonaApplication.class, args);
	}

}
