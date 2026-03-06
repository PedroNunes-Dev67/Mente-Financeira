package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTORequest(
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        @Schema(example = "exemplo@gmail.com")
        String email,
        @NotBlank(message = "Senha obrigatória")
        @Schema(example = "exemplo1234")
        String senha) {
}
