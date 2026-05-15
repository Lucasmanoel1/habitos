package com.lucasmanoel.habitos.controller;

import com.lucasmanoel.habitos.business.HabitosService;
import com.lucasmanoel.habitos.business.dto.CheckinDTORecords;
import com.lucasmanoel.habitos.business.dto.habitosDTORecords;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class HabitosController {

    private final HabitosService habitosService;

    @PostMapping
    @Operation(summary = "Cadastrar Habito", description = "Cadastro novo habito")
    @ApiResponse(responseCode = "201", description = "Habito cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<habitosDTORecords> cadastroHabito(@Valid @RequestBody habitosDTORecords dto){
        return ResponseEntity.status(HttpStatus.CREATED).body((habitosService.cadastroHabito(dto)));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Desativa Habito", description = "Desabilita Habito, mas permanece no sistema")
    @ApiResponse(responseCode = "200", description = "Habito Desativado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public void desativaHabito(@PathVariable String id){
        habitosService.desativaHabito(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta Habito", description = "Deleta Habito do sistema")
    @ApiResponse(responseCode = "204", description = "Habito Deletado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public void deletaHabito(@PathVariable String id){
        habitosService.deletaHabito(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera Habito", description = "Altera dados do habito")
    @ApiResponse(responseCode = "200", description = "Habito alterado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<habitosDTORecords> alteraHabito (@Valid @RequestBody habitosDTORecords dto,
                                                           @PathVariable String id){
        return ResponseEntity.ok(habitosService.alteraHabito(dto, id));
    }

    @PostMapping("/checkin")
    @Operation(summary = "Check-in", description = "Check-in diario")
    @ApiResponse(responseCode = "201", description = "Check-in realizado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<Integer> efetuarCheckin(@RequestParam String id){
        return ResponseEntity.ok(habitosService.efetuarCheckin(id));
    }

    @GetMapping
    @Operation(summary = "Historico Check-in", description = "Busca historico de check-ins")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<List<CheckinDTORecords>> historicoCheckin(@RequestParam String habitoId){
        return ResponseEntity.ok(habitosService.historicoCheckin(habitoId));
    }

    @GetMapping("/streak")
    @Operation(summary = "Sequencia", description = "Busca sequencia de check-ins")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<Integer> calcularStreak(@RequestParam String habitoId){
        return ResponseEntity.ok(habitosService.calcularStreak(habitoId));
    }

}
