package com.pagos.microservicio.config;

import com.pagos.microservicio.model.Pago;
import com.pagos.microservicio.repository.PagoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PagoRepository repository) {
        return args -> {
            // Si la tabla ya tiene datos, no duplicamos nada
            if (repository.count() == 0) {
                Faker faker = new Faker();
                Random random = new Random();

                // Opciones reales para tus métodos de pago
                List<String> metodos = List.of("EFECTIVO", "TARJETA", "PAYPAL");
                List<String> estados = List.of("APROBADO", "PENDIENTE", "RECHAZADO");

                System.out.println("🚀 Generando pagos de prueba con DataFaker...");

                for (int i = 0; i < 5; i++) {
                    Pago pagoFalso = new Pago();
                    // Genera un ID de orden aleatorio entre 1 y 100
                    pagoFalso.setOrdenId((long) faker.number().numberBetween(1, 100));
                    // Genera un monto decimal realista entre 10.0 y 500.0
                    pagoFalso.setMonto(faker.number().randomDouble(2, 10, 500));
                    // Elige un método y estado aleatorio de las listas
                    pagoFalso.setMetodoPago(metodos.get(random.nextInt(metodos.size())));
                    pagoFalso.setEstado(estados.get(random.nextInt(estados.size())));

                    repository.save(pagoFalso);
                }

                System.out.println("✅ ¡5 Pagos simulados insertados con éxito en la base de datos!");
            }
        };
    }
}