package com.lucasmanoel.habitos.business.mapper;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.infrasctruture.entity.habitosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface habitosUpdateConverter {
    void updateHabitos(habitosDTORecords dto, @MappingTarget habitosEntity entity);
}
