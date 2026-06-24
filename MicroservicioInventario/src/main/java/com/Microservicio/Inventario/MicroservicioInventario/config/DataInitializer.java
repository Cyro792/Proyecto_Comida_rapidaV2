package com.Microservicio.Inventario.MicroservicioInventario.config;

import com.Microservicio.Inventario.MicroservicioInventario.model.Producto;
import com.Microservicio.Inventario.MicroservicioInventario.repository.ProductoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository repository) {
        return args -> {
            // Si la base de datos ya tiene comida, no hace nada
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("es"));

                System.out.println("▓▒░ Generando productos aleatorios con DataFaker... ░▒▓");

                for (int i = 0; i < 10; i++) {
                    Producto producto = new Producto();

                    producto.setNombre(faker.food().dish());
                    producto.setPrecio(faker.number().randomDouble(2, 1500, 8990));
                    producto.setStock(faker.number().numberBetween(10, 100));

                    repository.save(producto);
                }

                System.out.println("▓▒░ ¡10 Productos creados con éxito! ░▒▓");
            }
        };
    }
}