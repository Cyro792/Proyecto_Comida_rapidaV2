package com.comidarapida.usuarios;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class UsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosApplication.class, args);
	}




@Bean(initMethod = "migrate")
@Profile("!test")
public Flyway flyway(DataSource dataSource) {
	return Flyway.configure()
			.dataSource(dataSource)
			.locations("classpath:db/migration")
			.baselineOnMigrate(true)
			.load();
	}

}