package com.lucasmanoel.habitos.business.mapper;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitosConverter {

    habitosDTORecords paraHabitosDTO(HabitosEntity entity);
}
