package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SenhaDTO(
        @NotBlank(message = "Senha obrigatória")
        @Schema(example = "exemplo1234")
        String senha) {
}
