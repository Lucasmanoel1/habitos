package com.lucasmanoel.habitos.business.dto;

import com.lucasmanoel.habitos.infrasctruture.entity.frequenciaEntity;
import com.lucasmanoel.habitos.infrasctruture.enums.StatushabitoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class habtiosDTO {

    private String id;
    private String nome;
    private String descricao;
}
