package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTORequest(
        @NotBlank(message = "Nome de usuário obrigatório")
        @Schema(example = "Pedro")
        String nome,
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        @Schema(example = "exemplo@gmail.com")
        String email,
        @NotBlank(message = "Senha obrigatória")
        @Size(min = 8, message = "Senha deve conter pelo menos 8 caracteres")
        @Schema(example = "exemplo1234")
        String senha
)
{}
