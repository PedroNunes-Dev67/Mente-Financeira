package PedroNunesDev.MenteFinanceira.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenVerificacaoDTO(
        @NotBlank String token
) {
}
