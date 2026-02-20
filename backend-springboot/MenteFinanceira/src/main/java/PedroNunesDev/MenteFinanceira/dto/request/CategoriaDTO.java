package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        @Schema(example = "Educação")
        @NotBlank(message = "Nome da categoria obrigatório") String nome
) {
}
