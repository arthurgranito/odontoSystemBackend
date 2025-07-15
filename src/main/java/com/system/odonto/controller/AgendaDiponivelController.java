package com.system.odonto.controller;

import com.system.odonto.model.Dentista;
import com.system.odonto.service.AgendaDiponivelService;
import com.system.odonto.shared.AgendaDiponivelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agenda")
public class AgendaDiponivelController {
    @Autowired
    private AgendaDiponivelService agendaDiponivelService;

    @PostMapping("/gerar")
    public List<AgendaDiponivelDTO> gerarAgenda(@RequestBody Map<String, Object> body) {
        Long dentistaId = Long.valueOf(body.get("dentistaId").toString());
        Long escalaId = Long.valueOf(body.get("escalaId").toString());
        LocalDate dataInicio = LocalDate.parse(body.get("dataInicio").toString());
        LocalDate dataFim = LocalDate.parse(body.get("dataFim").toString());
        return agendaDiponivelService.gerarAgenda(dentistaId, escalaId, dataInicio, dataFim);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<AgendaDiponivelDTO>> listarDisponiveis(@AuthenticationPrincipal Dentista dentista, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok().body(agendaDiponivelService.listarDisponiveis(dentista, dataInicio, dataFim));
    }

    @GetMapping("/disponiveis/{id}")
    public ResponseEntity<AgendaDiponivelDTO> buscar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return agendaDiponivelService.buscarPorId(id, dentista).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/disponiveis/{id}")
    public ResponseEntity<AgendaDiponivelDTO> atualizarDisponibilidade(@PathVariable Long id, @RequestBody Map<String, Object> body, @AuthenticationPrincipal Dentista dentista) {
        boolean disponivel = Boolean.parseBoolean(body.get("disponivel").toString());
        return ResponseEntity.ok(agendaDiponivelService.atualizarDisponibilidade(id, disponivel, dentista));
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirPorPeriodo(@RequestBody Map<String, Object> body) {
        Long dentistaId = Long.valueOf(body.get("dentistaId").toString());
        Long escalaId = Long.valueOf(body.get("escalaId").toString());
        LocalDate dataInicio = LocalDate.parse(body.get("dataInicio").toString());
        LocalDate dataFim = LocalDate.parse(body.get("dataFim").toString());
        Map<String, Integer> resultado = agendaDiponivelService.removerPorPeriodo(dentistaId, escalaId, dataInicio, dataFim);
        return ResponseEntity.ok(resultado);
    }
} 