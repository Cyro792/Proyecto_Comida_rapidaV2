package com.Microservicio.Inventario.MicroservicioInventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient // <-- Con esto el proyecto se registrará en el Eureka
public class MicroservicioInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioInventarioApplication.class, args);
	}
}