package com.lucasmanoel.habitos.business.mapper;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.infrasctruture.entity.habitosEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface habitosConverter {

    habitosEntity paraHabitoEntity(habitosDTORecords dtoRecords);

    habitosDTORecords paraHabitosDTO(habitosEntity entity);

    List<habitosEntity> paraListaHabitosEntity(List<habitosDTORecords> dto);

    List<habitosDTORecords> paraListaHabitosDTO(List<habitosEntity> entity);
}
