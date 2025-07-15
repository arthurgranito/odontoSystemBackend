package com.system.odonto.controller;

import com.system.odonto.model.Dentista;
import com.system.odonto.service.ConsultaService;
import com.system.odonto.shared.ConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<ConsultaDTO> listar(@AuthenticationPrincipal Dentista dentista,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return consultaService.listar(dentista, status, dataInicio, dataFim);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return consultaService.buscarPorId(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> agendar(@RequestBody Map<String, Object> body, @AuthenticationPrincipal Dentista dentista) {
        Long pacienteId = Long.valueOf(body.get("pacienteId").toString());
        Long tipoConsultaId = Long.valueOf(body.get("tipoConsultaId").toString());
        Long agendaDisponivelId = Long.valueOf(body.get("agendaDisponivelId").toString());
        String observacoes = body.get("observacoes") != null ? body.get("observacoes").toString() : null;
        return ResponseEntity.ok(consultaService.agendar(pacienteId, tipoConsultaId, agendaDisponivelId, observacoes, dentista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizar(@PathVariable Long id, @RequestBody ConsultaDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return consultaService.atualizar(id, dto, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<ConsultaDTO> concluir(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return consultaService.concluir(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaDTO> cancelar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return consultaService.cancelar(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/descancelar")
    public ResponseEntity<ConsultaDTO> descancelar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return consultaService.descancelar(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reagendar")
    public ResponseEntity<ConsultaDTO> reagendar(
            @PathVariable Long id,
            @RequestBody ConsultaDTO dto,
            @AuthenticationPrincipal Dentista dentista) {
        return consultaService.reagendarCompleto(id, dto, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        boolean removido = consultaService.remover(id, dentista);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
} 