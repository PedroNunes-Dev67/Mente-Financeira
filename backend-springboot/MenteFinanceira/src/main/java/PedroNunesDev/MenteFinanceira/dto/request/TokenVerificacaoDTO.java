package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenVerificacaoDTO(
        @NotBlank(message = "Token obrigatório")
        String token
) {
}
