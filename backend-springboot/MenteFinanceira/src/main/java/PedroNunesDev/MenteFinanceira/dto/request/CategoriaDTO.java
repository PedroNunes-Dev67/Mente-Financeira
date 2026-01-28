package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        @NotBlank String nome
) {
}
