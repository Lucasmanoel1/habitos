package com.lucasmanoel.habitos.infrasctruture.entity;

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
@Document("frequencia")
public class frequenciaEntity {

    @Id
    private String id;
    private int dia;
    private int semana;
    private int mes;
    private int ano;

}
