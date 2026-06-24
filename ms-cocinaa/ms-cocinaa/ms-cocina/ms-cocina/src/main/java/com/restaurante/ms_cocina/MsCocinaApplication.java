package com.restaurante.ms_cocina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCocinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCocinaApplication.class, args);

	}

}
