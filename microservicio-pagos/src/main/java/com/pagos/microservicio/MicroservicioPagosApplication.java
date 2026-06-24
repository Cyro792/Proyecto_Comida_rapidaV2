package com.pagos.microservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients     // Para comunicarte con otros microservicios (como Inventario)
@EnableDiscoveryClient // Para registrar este microservicio en Eureka
public class MicroservicioPagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioPagosApplication.class, args);
	}

}
