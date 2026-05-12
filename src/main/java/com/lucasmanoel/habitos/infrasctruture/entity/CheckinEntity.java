package com.lucasmanoel.habitos.infrasctruture.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document("checkin")
@AllArgsConstructor
@Builder
public class CheckinEntity {
    @Id
    private String id;
    private String HabitosId;
    private LocalDate data;
}
