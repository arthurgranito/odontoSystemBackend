package com.renato.odonto.shared;

import com.renato.odonto.model.Paciente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteDTO(
    Long id,
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,
    @NotBlank(message = "Telefone é obrigatório")
    String telefone,
    String email,
    LocalDate dataNascimento,
    String observacoes,
    Long dentistaId,
    LocalDateTime createdAt
) {
    public static PacienteDTO from(Paciente paciente) {
        return new PacienteDTO(
            paciente.getId(),
            paciente.getNome(),
            paciente.getTelefone(),
            paciente.getEmail(),
            paciente.getDataNascimento(),
            paciente.getObservacoes(),
            paciente.getDentista() != null ? paciente.getDentista().getId() : null,
                paciente.getCreatedAt()
        );
    }
}
