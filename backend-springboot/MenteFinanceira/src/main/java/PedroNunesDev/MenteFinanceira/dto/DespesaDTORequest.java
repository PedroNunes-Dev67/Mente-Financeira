package PedroNunesDev.MenteFinanceira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record DespesaDTORequest(
        @NotBlank String titulo,
        @NotNull BigDecimal valor,
        @NotNull Integer diaDePagamento,
        @NotNull Long id_categoria,
        @NotBlank String tipoDespesa
) {
}
