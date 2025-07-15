package com.system.odonto.model;

import com.system.odonto.shared.PacienteDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(unique = true)
    private String telefone;
    @Column(unique = true)
    private String email;
    private LocalDate dataNascimento;
    private String observacoes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    // Mapeamento One to Many para Consulta
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();

    @ManyToOne
    private Dentista dentista;

    public static Paciente from(PacienteDTO paciente) {
        return new Paciente(paciente.id(), paciente.nome(), paciente.telefone(), paciente.email(), paciente.dataNascimento(), paciente.observacoes());
    }

    public Paciente() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    public Paciente(Long id, String nome, String telefone, String email, LocalDate dataNascimento, String observacoes) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.observacoes = observacoes;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }
}
