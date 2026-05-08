package com.lucasmanoel.habitos.infrasctruture.entity;


import com.lucasmanoel.habitos.infrasctruture.enums.StatushabitoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("habitos")
public class habitosEntity {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private frequenciaEntity frequencia;
    private StatushabitoEnum statushabitoEnum;
    private LocalDateTime dataInicial;
}
