package com.ejemplo.productos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductosApplication.class, args);
    }

    @Bean
    CommandLineRunner verificarConexion(DataSource dataSource) {
        return args -> {
            try (Connection conexion = dataSource.getConnection()) {
                if (conexion.isValid(5)) {
                    System.out.println("Conexión a PostgreSQL exitosa");
                } else {
                    System.err.println("No se pudo validar la conexión con PostgreSQL.");
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con PostgreSQL: " + e.getMessage());
                if (e.getSQLState() != null) {
                    System.err.println("Código SQLState: " + e.getSQLState());
                }
            }
        };
    }
}
