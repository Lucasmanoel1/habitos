package com.lucasmanoel.habitos.business.mapper;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface habitosConverter {

    HabitosEntity paraHabitoEntity(habitosDTORecords dtoRecords);

    habitosDTORecords paraHabitosDTO(HabitosEntity entity);

    List<HabitosEntity> paraListaHabitosEntity(List<habitosDTORecords> dto);

    List<habitosDTORecords> paraListaHabitosDTO(List<HabitosEntity> entity);
}
