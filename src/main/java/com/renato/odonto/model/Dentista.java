package com.renato.odonto.model;

import com.renato.odonto.shared.DentistaDTO;
import com.renato.odonto.model.EscalaPadrao;
import com.renato.odonto.model.Consulta;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * Implementação de UserDetails para integração com Spring Security (autenticação por email e senha)
 */
@Entity
@Table(name = "tb_user")
public class Dentista implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;

    @OneToMany(mappedBy = "dentista")
    private final List<TipoConsulta> tipoConsultas = new ArrayList<>();

    @OneToMany(mappedBy = "dentista")
    private final List<EscalaPadrao> escalaPadraos = new ArrayList<>();

    @OneToMany(mappedBy = "dentista")
    private final List<AgendaDiponivel> agendasDisponiveis = new ArrayList<>();

    @OneToMany(mappedBy = "dentista")
    private final List<Consulta> consultas = new ArrayList<>();

    public static Dentista from(DentistaDTO user) {
        return new Dentista(user.id(), user.nome(), user.email(), user.senha());
    }

    public Dentista() {
    }

    public Dentista(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    /**
     * Construtor utilizado para autenticação (login)
     */
    public Dentista(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<TipoConsulta> getTipoConsultas() {
        return tipoConsultas;
    }

    public List<EscalaPadrao> getEscalaPadraos() {
        return escalaPadraos;
    }

    public List<AgendaDiponivel> getAgendasDisponiveis() {
        return agendasDisponiveis;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    /**
     * Não há perfis/roles definidos para o dentista
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.Collections.emptyList();
    }

    /**
     * Retorna o email do dentista para autenticação
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Conta nunca expira
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Conta nunca é bloqueada
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Credenciais nunca expiram
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Conta sempre está habilitada
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Retorna o hash da senha do dentista
     */
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.senha;
    }

    /**
     * Define o hash da senha do dentista
     */
    public void setPassword(String senha) {
        this.senha = senha;
    }

    /**
     * Cria uma instância de Dentista para autenticação a partir do email e senha
     */
    public static Dentista fromEmailSenha(String email, String senha) {
        return new Dentista(email, senha);
    }
}
