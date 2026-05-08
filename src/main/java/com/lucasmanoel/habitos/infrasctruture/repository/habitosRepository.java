package com.lucasmanoel.habitos.infrasctruture.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface habitosRepository extends MongoRepository {
}
