package com.system.odonto.shared;

import com.system.odonto.model.EscalaPadrao;
import com.system.odonto.model.enums.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record EscalaPadraoDTO(
    Long id,
    @NotNull(message = "Dia da semana é obrigatório")
    DiaSemana diaSemana,
    @NotNull(message = "Hora de início é obrigatória")
    LocalTime horaInicio,
    @NotNull(message = "Hora de fim é obrigatória")
    LocalTime horaFim,
    @NotNull(message = "Intervalo é obrigatório")
    Integer intervaloMinutos,
    DentistaDTO dentista
) {
    public static EscalaPadraoDTO from(EscalaPadrao escala) {
        return new EscalaPadraoDTO(escala.getId(), escala.getDiaSemana(), escala.getHoraInicio(), escala.getHoraFim(), escala.getIntervaloMinutos(), DentistaDTO.from(escala.getDentista()));
    }
}
