package com.lucasmanoel.habitos.business;

import com.lucasmanoel.habitos.business.dto.CheckinDTORecords;
import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import com.lucasmanoel.habitos.business.mapper.HabitosConverter;
import com.lucasmanoel.habitos.business.mapper.HabitosUpdateConverter;
import com.lucasmanoel.habitos.infrasctruture.entity.CheckinEntity;
import com.lucasmanoel.habitos.infrasctruture.entity.HabitosEntity;
import com.lucasmanoel.habitos.infrasctruture.exceptions.ConflictException;
import com.lucasmanoel.habitos.infrasctruture.exceptions.ResourceNotFoundException;
import com.lucasmanoel.habitos.infrasctruture.repository.CheckinRepository;
import com.lucasmanoel.habitos.infrasctruture.repository.HabitosRepository;
import com.lucasmanoel.habitos.infrasctruture.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitosService {
    private  final HabitosConverter habitosConverter;
    private  final HabitosUpdateConverter habitosUpdateConverter;
    private  final HabitosRepository habitosRepository;
    private  final CheckinRepository checkinRepository;

    public habitosDTORecords cadastroHabito(habitosDTORecords dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
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
        if(!entity.getAtivo()){
            throw new ConflictException("Hábito já desativado");
        }
        entity.setAtivo(false);
        habitosRepository.save(entity);
    }

    public void deletaHabito(String id){
        if (!habitosRepository.existsById(id)){
            throw new ResourceNotFoundException("Id não encontrado");
        }
        habitosRepository.deleteById(id);
    }

    public habitosDTORecords alteraHabito(habitosDTORecords dto, String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        if (!entity.getAtivo()){
            throw new ConflictException("Hábito inativo");
        }
        habitosUpdateConverter.updateHabitos(dto, entity);
        return habitosConverter.paraHabitosDTO(habitosRepository.save(entity));
    }

    public int efetuarCheckin(String id){
        HabitosEntity entity = habitosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        if (!entity.getAtivo()){
            throw new ConflictException("Hábito desativado!");
        }
        if (checkinRepository.existsByHabitosIDAndData(id, LocalDate.now())){
            throw new ConflictException("Você já realizou Check-in neste hábito hoje");
        }
        CheckinEntity checkin = CheckinEntity.builder()
                .habitosID(entity.getHabitosID())
                .data(LocalDate.now())
                .build();
        checkinRepository.save(checkin);
        return calcularStreak(entity.getHabitosID());
    }

    public List<CheckinDTORecords> historicoCheckin(String habitoId){
        if (!habitosRepository.existsById(habitoId)){
            throw new ResourceNotFoundException("Id do hábito não encontrado");
        }
        LocalDate data = LocalDate.now().minusDays(30);
        List<CheckinEntity> checkins = checkinRepository.findByHabitosIDAndDataAfter(habitoId, data);
        if (checkins.stream().map(c -> new CheckinDTORecords(c.getData())).toList().isEmpty()){
            throw new ConflictException("Você ainda não fez check-in nesse hábito");
        }
        return checkins.stream().map(c -> new CheckinDTORecords(c.getData())).toList();
   }

    public int calcularStreak(String habitoId){
        List<CheckinEntity> buscarLista = checkinRepository.findByHabitosID(habitoId);
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
