package com.system.odonto.controller;

import com.system.odonto.model.Dentista;
import com.system.odonto.service.EscalaPadraoService;
import com.system.odonto.shared.EscalaPadraoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/escalas")
public class EscalaPadraoController {
    @Autowired
    private EscalaPadraoService escalaPadraoService;

    @GetMapping
    public List<EscalaPadraoDTO> listar(@AuthenticationPrincipal Dentista dentista) {
        return escalaPadraoService.listarTodos(dentista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscalaPadraoDTO> buscar(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        return escalaPadraoService.buscarPorId(id, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EscalaPadraoDTO> criar(@RequestBody @Valid EscalaPadraoDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return ResponseEntity.ok(escalaPadraoService.criar(dto, dentista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscalaPadraoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EscalaPadraoDTO dto, @AuthenticationPrincipal Dentista dentista) {
        return escalaPadraoService.atualizar(id, dto, dentista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id, @AuthenticationPrincipal Dentista dentista) {
        boolean removido = escalaPadraoService.remover(id, dentista);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
} 