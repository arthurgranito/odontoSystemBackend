package com.system.odonto.controller;

import com.system.odonto.model.Dentista;
import com.system.odonto.service.ConsultaService;
import com.system.odonto.shared.ConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/resumo")
    public ResponseEntity<Map<String, Object>> resumo(@AuthenticationPrincipal Dentista dentista,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        LocalDateTime inicio = dataInicio != null ? dataInicio.atStartOfDay() : LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fim = dataFim != null ? dataFim.plusDays(1).atStartOfDay().minusSeconds(1) : LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1);
        List<ConsultaDTO> concluidas = consultaService.listar(dentista, "Concluída", inicio, fim);
        double faturamento = concluidas.stream().mapToDouble(c -> c.valorCobrado() != null ? c.valorCobrado() : 0.0).sum();
        long totalAtendimentos = concluidas.size();
        // Pacientes novos no mês: simplificado para consultas concluídas com paciente distinto
        long pacientesNovos = concluidas.stream().map(c -> c.paciente().id()).distinct().count();
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("totalAtendimentos", totalAtendimentos);
        resumo.put("faturamentoMes", faturamento);
        resumo.put("pacientesNovosMes", pacientesNovos);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/historico-consultas")
    public List<ConsultaDTO> historicoConsultas(@AuthenticationPrincipal Dentista dentista,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return consultaService.listar(dentista, "Concluída", dataInicio, dataFim);
    }
} 