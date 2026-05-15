package com.lucasmanoel.habitos.business.mapper;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HabitosUpdateConverter {
    void updateHabitos(habitosDTORecords dto, @MappingTarget HabitosEntity entity);
}
