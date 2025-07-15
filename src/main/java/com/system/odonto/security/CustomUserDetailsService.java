package com.system.odonto.security;

import com.system.odonto.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private DentistaRepository dentistaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return dentistaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Dentista não encontrado com email: " + email));
    }
} 