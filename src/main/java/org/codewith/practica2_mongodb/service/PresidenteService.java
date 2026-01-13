package org.codewith.practica2_mongodb.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.codewith.practica2_mongodb.model.Presidente;
import org.codewith.practica2_mongodb.repository.PresidenteRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresidenteService {

    private final PresidenteRepository presidenteRepo;

    public PresidenteService(PresidenteRepository presidenteRepo) {
        this.presidenteRepo = presidenteRepo;
    }

    public void crearPresidente(Presidente presidente) {presidenteRepo.save(presidente);}

    public List<Presidente> importarDesdeJSON(String ruta) throws IOException {
        try (Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream())) {
            Type listType = new TypeToken<ArrayList<Presidente>>() {}.getType();
            List<Presidente> list_presidente = new Gson().fromJson(reader, listType);

            return presidenteRepo.saveAll(list_presidente);
        }
    }

    public List<Presidente> buscarPresidentes() {
        return presidenteRepo.findAll();
    }

    public List<Presidente> listarPresidentes() {
        List<Presidente> lista = presidenteRepo.findAll();
        System.out.println("--- Lista de Presidentes ---");
        for (Presidente p : lista) {
            System.out.println(p);
        }
        return lista;
    }

    public void borrarPresidentes() {
        presidenteRepo.deleteAll();
    }



}
