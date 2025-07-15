package com.renato.odonto.shared;

import com.renato.odonto.model.Consulta;
import com.renato.odonto.model.enums.ConsultaStatus;

import java.time.LocalDateTime;

public record ConsultaDTO(
    Long id,
    DentistaDTO dentista,
    PacienteDTO paciente,
    TipoConsultaDTO tipoConsulta,
    AgendaDiponivelDTO agendaDisponivel,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    String observacoes,
    ConsultaStatus status,
    Double valorCobrado,
    LocalDateTime dataConclusao
) {
    public static ConsultaDTO from(Consulta consulta) {
        return new ConsultaDTO(
            consulta.getId(),
            DentistaDTO.from(consulta.getDentista()),
            PacienteDTO.from(consulta.getPaciente()),
            TipoConsultaDTO.from(consulta.getTipoConsulta()),
            consulta.getAgendaDisponivel() != null ? AgendaDiponivelDTO.from(consulta.getAgendaDisponivel()) : null,
            consulta.getDataHoraInicio(),
            consulta.getDataHoraFim(),
            consulta.getObservacoes(),
            consulta.getStatus(),
            consulta.getValorCobrado(),
            consulta.getDataConclusao()
        );
    }
} 