package com.renato.odonto.model;

import com.renato.odonto.model.enums.ConsultaStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dentista_id")
    private Dentista dentista;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "tipo_consulta_id")
    private TipoConsulta tipoConsulta;

    @OneToOne
    @JoinColumn(name = "agenda_disponivel_id")
    private AgendaDiponivel agendaDisponivel;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private ConsultaStatus status;
    private Double valorCobrado;
    private LocalDateTime dataConclusao;

    public Consulta() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Dentista getDentista() { return dentista; }
    public void setDentista(Dentista dentista) { this.dentista = dentista; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public TipoConsulta getTipoConsulta() { return tipoConsulta; }
    public void setTipoConsulta(TipoConsulta tipoConsulta) { this.tipoConsulta = tipoConsulta; }

    public AgendaDiponivel getAgendaDisponivel() { return agendaDisponivel; }
    public void setAgendaDisponivel(AgendaDiponivel agendaDisponivel) { this.agendaDisponivel = agendaDisponivel; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public ConsultaStatus getStatus() { return status; }
    public void setStatus(ConsultaStatus status) { this.status = status; }

    public Double getValorCobrado() { return valorCobrado; }
    public void setValorCobrado(Double valorCobrado) { this.valorCobrado = valorCobrado; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }
} 