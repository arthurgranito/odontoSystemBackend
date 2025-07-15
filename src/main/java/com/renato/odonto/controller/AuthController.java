package com.renato.odonto.controller;

import com.renato.odonto.model.Dentista;
import com.renato.odonto.service.AuthService;
import com.renato.odonto.shared.DentistaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Novo DTO para login
record LoginRequest(@jakarta.validation.constraints.Email(message = "Email inválido") String email,
                   @jakarta.validation.constraints.NotBlank(message = "Senha é obrigatória") String senha) {}

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = authService.login(loginRequest.email(), loginRequest.senha());
        Dentista user = authService.getDentistaByEmail(loginRequest.email());
        return ResponseEntity.ok().body(java.util.Map.of(
            "token", token,
            "user", com.renato.odonto.shared.DentistaDTO.from(user)
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<DentistaDTO> register(@RequestBody @Valid DentistaDTO registerRequest) {
        Dentista dentista = new Dentista(null, registerRequest.nome(), registerRequest.email(), registerRequest.senha());
        Dentista saved = authService.register(dentista);
        return ResponseEntity.ok(DentistaDTO.from(saved));
    }
} 