package PedroNunesDev.MenteFinanceira.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenVerificacaoDtoResponse(
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        String token
) {
}
