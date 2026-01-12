package org.codewith.practica2_mongodb;

import org.codewith.practica2_mongodb.service.PaisService;
import org.codewith.practica2_mongodb.service.PresidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Secuencia {
    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private PaisService paisService;

    public void ejecutar()  {

        paisService.borrarPasises();
        presidenteService.borrarPresidentes();

        System.out.println("--- Importar datos desde JSON ---");
        try {
            presidenteService.importarDesdeJSON("presidentes.json");
            paisService.importarDesdeJSON("paises.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("--- Mostrar datos ---");
        presidenteService.listarPresidentes();
        paisService.listarPaises();

        System.out.println("--- Modificar datos ---");
        paisService.modificarOrganizacionPais("pais3", "ASDESA");

        System.out.println("--- Mostrar datos modificados ---");
        paisService.listarPaises();

        System.out.println("--- Eliminar datos ---");
        paisService.borrarPasises();
        presidenteService.borrarPresidentes();

        System.out.println("--- Mostrar datos despues de eliminar todo---");
        presidenteService.listarPresidentes();
        paisService.listarPaises();
    }
}
