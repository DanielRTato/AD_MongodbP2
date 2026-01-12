package org.codewith.practica2_mongodb;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Practica2MongodbApplication {

    private final Secuencia secuencia;

    public Practica2MongodbApplication(Secuencia secuencia) {
        this.secuencia = secuencia;
    }

    @PostConstruct
    public void ejecutarSolucion() {
        secuencia.ejecutar();
        // System.exit(200); // comentado para no forzar la salida del proceso durante pruebas
    }

    public static void main(String[] args) {
        SpringApplication.run(Practica2MongodbApplication.class, args);
    }

}
