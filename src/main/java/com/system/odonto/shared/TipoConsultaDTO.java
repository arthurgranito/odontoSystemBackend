package com.system.odonto.shared;

import com.system.odonto.model.TipoConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoConsultaDTO(
    Long id,
    @NotBlank(message = "Nome do procedimento é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,
    @NotNull(message = "Preço é obrigatório")
    Double preco,
    Integer duracaoEstimadaMinutos,
    DentistaDTO dentistaDTO
) {
    public static TipoConsultaDTO from(TipoConsulta tipoConsulta) {
        return new TipoConsultaDTO(tipoConsulta.getId(), tipoConsulta.getNome(), tipoConsulta.getPreco(), tipoConsulta.getDuracaoMinutos(), DentistaDTO.from(tipoConsulta.getDentista()));
    }
}
