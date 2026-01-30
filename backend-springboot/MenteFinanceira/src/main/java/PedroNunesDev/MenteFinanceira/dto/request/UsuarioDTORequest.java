package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTORequest(
        @NotBlank(message = "Nome de usuário obrigatório")
        String nome,
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "Senha obrigatória")
        @Size(min = 8, message = "Senha deve conter pelo menos 8 caracteres")
        String senha
)
{}
