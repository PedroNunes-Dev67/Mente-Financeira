package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SenhaDTO(
        @NotBlank(message = "Senha obrigatória")
        String senha) {
}
