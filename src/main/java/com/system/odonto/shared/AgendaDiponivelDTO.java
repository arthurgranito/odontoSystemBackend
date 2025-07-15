package com.system.odonto.shared;

import com.system.odonto.model.AgendaDiponivel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDateTime;

public record AgendaDiponivelDTO(
    Long id,
    DentistaDTO dentista,
    LocalDate data,
    LocalTime horaInicio,
    LocalTime horaFinal,
    Boolean disponivel,
    EscalaPadraoDTO escala,
    List<LocalDateTime> horariosDisponiveis
) {
    public static AgendaDiponivelDTO from(AgendaDiponivel agenda) {
        return new AgendaDiponivelDTO(
            agenda.getId(),
            DentistaDTO.from(agenda.getDentista()),
            agenda.getData(),
            agenda.getHoraInicio(),
            agenda.getHoraFim(),
            agenda.getDisponivel(),
            agenda.getEscala() != null ? EscalaPadraoDTO.from(agenda.getEscala()) : null,
            agenda.getHorariosDisponiveis()
        );
    }
} 