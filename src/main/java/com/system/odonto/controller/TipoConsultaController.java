package com.system.odonto.controller;

import com.system.odonto.model.Dentista;
import com.system.odonto.service.TipoConsultaService;
import com.system.odonto.shared.TipoConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/tipos-consulta")
public class TipoConsultaController {
    @Autowired
    private TipoConsultaService tipoConsultaService;

    @GetMapping
    public List<TipoConsultaDTO> listar(@AuthenticationPrincipal Dentista dentista) {
        return tipoConsultaService.listarTodos(dentista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoConsultaDTO> buscar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return tipoConsultaService.buscarPorId(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoConsultaDTO> criar(@RequestBody @Valid TipoConsultaDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return ResponseEntity.ok(tipoConsultaService.criar(dto, dentista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoConsultaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TipoConsultaDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return tipoConsultaService.atualizar(id, dto, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        boolean removido = tipoConsultaService.remover(id, dentista);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
} 