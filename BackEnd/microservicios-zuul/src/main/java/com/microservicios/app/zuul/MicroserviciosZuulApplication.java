package com.microservicios.app.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class MicroserviciosZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosZuulApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
    	return new WebMvcConfigurer() {
        	public void addCorsMappings(CorsRegistry registry) {
           		registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST","OPTIONS").allowCredentials(false);
        }};
    
}}
