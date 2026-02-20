package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TokenVerificacaoDTORequest(
        @NotBlank(message = "Token obrigatório")
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        String token
) {
}
