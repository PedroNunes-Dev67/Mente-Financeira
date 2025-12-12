package PedroNunesDev.MenteFinanceira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FinancaDTORequest(
        @NotBlank String titulo,
        @NotNull Double valor,
        @NotNull Long id_categoria
) {
}
