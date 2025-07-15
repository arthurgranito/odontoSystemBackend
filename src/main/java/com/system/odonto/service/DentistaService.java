package com.system.odonto.service;

import com.system.odonto.model.Dentista;
import com.system.odonto.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DentistaService {
    @Autowired
    private DentistaRepository dentistaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Dentista atualizarDados(Dentista dentista, String nome, String senha) {
        dentista.setNome(nome);
        if (senha != null && !senha.isBlank()) {
            dentista.setPassword(passwordEncoder.encode(senha));
        }
        return dentistaRepository.save(dentista);
    }
} 