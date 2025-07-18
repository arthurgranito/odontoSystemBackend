package com.system.odonto.shared;

import com.system.odonto.model.Dentista;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DentistaDTO(
    Long id,
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    String senha
) {
    public static DentistaDTO from(Dentista user) {
        return new DentistaDTO(user.getId(), user.getNome(), user.getEmail(), user.getSenha());
    }
}
