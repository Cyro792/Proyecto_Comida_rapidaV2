package com.comidarapida.usuarios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=" +
				"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
				"org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," +
				"org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration," +
				"org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
class UsuariosApplicationTests {

	@Test
	void contextLoads() {
		// Con las exclusiones de arriba, Spring Boot levantará el contexto
		// a la perfección sin intentar buscar bases de datos, repositorios ni migraciones.
	}
}