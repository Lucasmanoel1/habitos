package com.lucasmanoel.habitos.business;

import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.business.mapper.HabitosConverter;
import com.lucasmanoel.habitos.business.mapper.HabitosUpdateConverter;
import com.lucasmanoel.habitos.infrasctruture.entity.CheckinEntity;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import com.lucasmanoel.habitos.infrasctruture.exceptions.ResourceNotFoundException;
import com.lucasmanoel.habitos.infrasctruture.repository.CheckinRepository;
import com.lucasmanoel.habitos.infrasctruture.repository.HabitosRepository;
import com.lucasmanoel.habitos.infrasctruture.security.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitosService {
    private  final JwtUtil jwtUtil;
    private  final HabitosConverter habitosConverter;
    private  final HabitosUpdateConverter habitosUpdateConverter;
    private  final HabitosRepository habitosRepository;
    private  final CheckinRepository checkinRepository;

    public habitosDTORecords cadastroHabito(String token, habitosDTORecords dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        HabitosEntity entity = HabitosEntity.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .email(email)
                .dataCriacao(LocalDateTime.now())
                .ativo(true)
                .build();

        return habitosConverter.paraHabitosDTO(habitosRepository.save(entity));
    }

    public void desativaHabito(String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );

        entity.setAtivo(false);
        habitosRepository.save(entity);
    }

    public void deletaHabito(String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        habitosRepository.deleteById(id);
    }

    public habitosDTORecords alteraHabito(habitosDTORecords dto, String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        habitosUpdateConverter.updateHabitos(dto, entity);
        return habitosConverter.paraHabitosDTO(habitosRepository.save(entity));
    }

    public int efetuarCheckin(String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        CheckinEntity checkin = CheckinEntity.builder()
                .HabitosId(entity.getId())
                .data(LocalDate.now())
                .build();
        checkinRepository.save(checkin);
        return calcularStreak(entity.getId());
    }

    public List<CheckinEntity> historicoCheckin(String habitoId){
        LocalDate data = LocalDate.now().minusDays(30);
       return checkinRepository.findByHabitosIDAndDataAfter(habitoId, data);
   }

    public int calcularStreak(String habitoId){
        List<CheckinEntity> buscarLista = checkinRepository.findByHabitosId(habitoId);
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
