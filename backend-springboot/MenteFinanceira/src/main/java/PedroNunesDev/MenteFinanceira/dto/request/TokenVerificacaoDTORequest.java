package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenVerificacaoDTORequest(
        @NotBlank(message = "Token obrigatório")
        String token
) {
}
