package com.lucasmanoel.habitos.infrasctruture.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("habitos")
public class HabitosEntity {

    @Id
    private String id;
    private String nome;
    private String email;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Boolean ativo;

}
