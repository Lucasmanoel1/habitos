package com.lucasmanoel.habitos.business;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.business.mapper.habitosConverter;
import com.lucasmanoel.habitos.business.mapper.habitosUpdateConverter;
import com.lucasmanoel.habitos.infrasctruture.entity.CheckinEntity;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import com.lucasmanoel.habitos.infrasctruture.exceptions.ResourceNotFoundException;
import com.lucasmanoel.habitos.infrasctruture.repository.habitosRepository;
import com.lucasmanoel.habitos.infrasctruture.security.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class habitosService {
    private JwtUtil jwtUtil;
    private static  habitosConverter habitosConverter;
    private habitosUpdateConverter habitosUpdateConverter;
    private habitosRepository habitosRepository;

    public void cadastroHabito(String token, habitosDTORecords dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        HabitosEntity entity = HabitosEntity.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .email(email)
                .dataCriacao(LocalDateTime.now())
                .ativo(true)
                .build();
        habitosRepository.save(entity);
    }

    public void DesativaHabito(String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );

        entity.setAtivo(false);
        habitosUpdateConverter.desativaHabito(entity);
        habitosConverter.paraHabitosDTO(habitosRepository.save(entity));
    }

    public void DeletaHabito(String id){
        habitosRepository.deleteById(id);
    }

    public habitosDTORecords AlteraHabito(habitosDTORecords dto, String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        habitosUpdateConverter.updateHabitos(dto, entity);
        return habitosConverter.paraHabitosDTO(habitosRepository.save(entity));
    }

    public void efetuarCheckin(String id){
        CheckinEntity.builder()
                .HabitosId(id)
                .data(LocalDate.now())
                .build();
    }

   public List<CheckinEntity> historicoCheckin(String habitoId){
        LocalDate data = LocalDate.now().minusDays(30);
       return habitosRepository.findByHabitosIDAndDataAfter(habitoId, data);
   }
   public int calcularStreak(String habitoId){
        List<CheckinEntity> buscarLista = habitosRepository.findByHabitosId(habitoId);
        buscarLista.sort(Comparator.comparing(CheckinEntity::getData).reversed());

        LocalDate dataEsperada = LocalDate.now();
        int streak = 0;

        for (CheckinEntity checkin : buscarLista){
            if (checkin.getData().equals(dataEsperada)){
                streak++;
                dataEsperada = dataEsperada.minusDays(1);
            }else if (checkin.getData().isBefore(dataEsperada)){
                break;
            }
        }
        return streak;
   }


}
