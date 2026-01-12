package org.codewith.practica2_mongodb.service;

import com.google.gson.Gson;
import org.codewith.practica2_mongodb.model.Pais;
import org.codewith.practica2_mongodb.repository.PaisRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class PaisService {

    private final PaisRepository paisRepo;

    public PaisService(PaisRepository paisRepo) {
        this.paisRepo = paisRepo;
    }

    public void crearPais(Pais pais) {
        paisRepo.save(pais);
    }

    public List<Pais> buscarPaises() {
        return paisRepo.findAll();
    }

    public List<Pais> importarDesdeJSON(String ruta) throws IOException {
        try (Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream())) {
            Type listType = new com.google.gson.reflect.TypeToken<List<Pais>>() {
            }.getType();
            List<Pais> list_pais = new Gson().fromJson(reader, listType);

            return paisRepo.saveAll(list_pais);
        }
    }

    public void modificarOrganizacionPais(String idPais, String nuevaOrganizacion) {
        Pais pais = paisRepo.findById(idPais).orElse(null);

        if (pais != null) {
            pais.setOrganizacion(nuevaOrganizacion);
            paisRepo.save(pais); // Al guardar con el mismo ID, machaca el dato anterior (Update)
            System.out.println("País actualizado: " + pais.getNome());
        } else {
            System.out.println("No se encontró el país con ID: " + idPais);
        }
    }

    public void borrarPasises() {
        paisRepo.deleteAll();
    }

    public List<Pais> listarPaises() {
        List<Pais> lista = paisRepo.findAll();
        System.out.println("--- Lista de Paises ---");
        for (Pais p : lista) {
            System.out.println(p);
        }
        return lista;
    }

}
