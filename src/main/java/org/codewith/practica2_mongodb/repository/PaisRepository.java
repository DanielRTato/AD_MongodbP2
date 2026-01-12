package org.codewith.practica2_mongodb.repository;

import org.codewith.practica2_mongodb.model.Pais;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaisRepository extends MongoRepository<Pais, String> {
}
