package com.lucasmanoel.habitos.infrasctruture.repository;

import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitosRepository extends MongoRepository<HabitosEntity, String> {

}
