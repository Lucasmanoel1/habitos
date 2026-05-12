package com.lucasmanoel.habitos.infrasctruture.repository;

import com.lucasmanoel.habitos.infrasctruture.entity.CheckinEntity;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitosRepository extends MongoRepository<HabitosEntity, String> {


    List<HabitosEntity> findByEmail(String email);
    List<CheckinEntity> findByHabitosIDAndDataAfter(String habitoId, LocalDate data);
    List<CheckinEntity> findByHabitosId(String habitoId);
}
