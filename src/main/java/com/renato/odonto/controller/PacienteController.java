package com.renato.odonto.controller;

import com.renato.odonto.model.Dentista;
import com.renato.odonto.service.PacienteService;
import com.renato.odonto.shared.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<PacienteDTO> listar(@AuthenticationPrincipal Dentista dentista) {
        return pacienteService.listarTodos(dentista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return pacienteService.buscarPorId(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> criar(@RequestBody @Valid PacienteDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return ResponseEntity.ok(pacienteService.criar(dto, dentista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PacienteDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return pacienteService.atualizar(id, dto, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        boolean removido = pacienteService.remover(id, dentista);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
} 