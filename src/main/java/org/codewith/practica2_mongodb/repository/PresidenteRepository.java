package org.codewith.practica2_mongodb.repository;

import org.codewith.practica2_mongodb.model.Presidente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PresidenteRepository extends MongoRepository<Presidente, String> {
}
