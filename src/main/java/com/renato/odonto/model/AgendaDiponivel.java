package com.renato.odonto.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import com.renato.odonto.model.Consulta;

@Entity
@Table(name = "tb_agenda")
public class AgendaDiponivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dentista dentista;

    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    private Boolean disponivel;

    @OneToOne(mappedBy = "agendaDisponivel")
    private Consulta consulta;

    @ManyToOne
    private EscalaPadrao escala;

    @Transient
    public List<LocalDateTime> getHorariosDisponiveis() {
        List<LocalDateTime> horarios = new ArrayList<>();
        if (escala == null || data == null || horaInicio == null || horaFim == null || horaFim.isBefore(horaInicio)) return null;
        if (escala.getHoraInicio() == null || escala.getHoraFim() == null || escala.getIntervaloMinutos() == null) return null;
        for (LocalTime hora = horaInicio;
             !hora.isAfter(horaFim.minusMinutes(escala.getIntervaloMinutos()));
             hora = hora.plusMinutes(escala.getIntervaloMinutos())) {
            horarios.add(LocalDateTime.of(data, hora));
        }
        return horarios;
    }

    public AgendaDiponivel() {
    }

    public AgendaDiponivel(Long id, Dentista dentista, LocalDate data, Boolean disponivel, EscalaPadrao escala) {
        this.id = id;
        this.dentista = dentista;
        this.data = data;
        this.horaInicio = escala.getHoraInicio();
        this.horaFim = escala.getHoraFim();
        this.disponivel = disponivel;
        this.escala = escala;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public EscalaPadrao getEscala() {
        return escala;
    }

    public void setEscala(EscalaPadrao escala) {
        this.escala = escala;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
