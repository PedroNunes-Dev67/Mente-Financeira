package PedroNunesDev.MenteFinanceira.dto;

import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.FinancaStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FinancaDTORequest(
        @NotBlank String titulo,
        @NotNull Integer valor,
        @NotBlank FinancaStatus status,
        @NotNull Usuario usuario) {
}
