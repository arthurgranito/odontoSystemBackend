package com.system.odonto.service;

import com.system.odonto.model.Dentista;
import com.system.odonto.repository.DentistaRepository;
import com.system.odonto.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DentistaRepository dentistaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String login(String email, String senha) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );
        return jwtTokenProvider.generateToken(email);
    }

    public Dentista register(Dentista dentista) {
        if (dentistaRepository.findByEmail(dentista.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        dentista.setPassword(passwordEncoder.encode(dentista.getPassword()));
        return dentistaRepository.save(dentista);
    }

    public Dentista getDentistaByEmail(String email) {
        return dentistaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Dentista não encontrado"));
    }
} 