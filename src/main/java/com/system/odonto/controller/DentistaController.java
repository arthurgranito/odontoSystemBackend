package com.system.odonto.controller;

import com.system.odonto.model.Dentista;
import com.system.odonto.service.DentistaService;
import com.system.odonto.shared.DentistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dentista")
public class DentistaController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DentistaService dentistaService;

    @GetMapping("/me")
    public ResponseEntity<DentistaDTO> getMe(@AuthenticationPrincipal Dentista dentista) {
        return ResponseEntity.ok(DentistaDTO.from(dentista));
    }

    @PutMapping("/me")
    public ResponseEntity<DentistaDTO> updateMe(@AuthenticationPrincipal Dentista dentista,
                                                @RequestBody @Valid DentistaDTO updateRequest) {
        Dentista atualizado = dentistaService.atualizarDados(dentista, updateRequest.nome(), updateRequest.senha());
        return ResponseEntity.ok(DentistaDTO.from(atualizado));
    }

    @PostMapping("/login")
    public ResponseEntity<DentistaDTO> login(@RequestBody @Valid DentistaDTO loginRequest) {
        // Implementation of login method
        return null; // Placeholder return, actual implementation needed
    }

    @PostMapping("/register")
    public ResponseEntity<DentistaDTO> register(@RequestBody @Valid DentistaDTO registerRequest) {
        // Implementation of register method
        return null; // Placeholder return, actual implementation needed
    }
} 