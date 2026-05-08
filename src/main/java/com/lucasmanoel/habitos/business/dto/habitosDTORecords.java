package com.lucasmanoel.habitos.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucasmanoel.habitos.infrasctruture.entity.frequenciaEntity;
import com.lucasmanoel.habitos.infrasctruture.enums.StatushabitoEnum;

import java.time.LocalDateTime;

public record habitosDTORecords(
        String id,
        String nome,
        String descricao,
        frequenciaEntity frequencia,
        StatushabitoEnum statushabitoEnum,
         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
         LocalDateTime dataInicial
) {
}
