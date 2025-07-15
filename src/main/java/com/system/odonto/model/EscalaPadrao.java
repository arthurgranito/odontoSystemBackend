package com.system.odonto.model;

import com.system.odonto.model.enums.DiaSemana;
import com.system.odonto.shared.EscalaPadraoDTO;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_escala_padrao")
public class EscalaPadrao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    private Integer intervaloMinutos;

    @ManyToOne
    private Dentista dentista;

    public List<AgendaDiponivel> getAgendasDisponiveis() {
        return agendasDisponiveis;
    }

    @OneToMany(mappedBy = "escala", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AgendaDiponivel> agendasDisponiveis = new ArrayList<>();

    public static EscalaPadrao from(EscalaPadraoDTO escalaPadraoDTO) {
        return new EscalaPadrao(escalaPadraoDTO.id(), escalaPadraoDTO.diaSemana(), escalaPadraoDTO.horaInicio(), escalaPadraoDTO.horaFim(), escalaPadraoDTO.intervaloMinutos(), Dentista.from(escalaPadraoDTO.dentista()));
    }

    public EscalaPadrao() {
    }

    public EscalaPadrao(Long id, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim, Integer intervaloMinutos, Dentista dentista) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.intervaloMinutos = intervaloMinutos;
        this.dentista = dentista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getIntervaloMinutos() {
        return intervaloMinutos;
    }

    public void setIntervaloMinutos(Integer intervaloMinutos) {
        this.intervaloMinutos = intervaloMinutos;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }
}
