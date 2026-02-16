package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record DespesaDTORequest(
        @NotBlank(message = "Titulo da despesa obrigatório") String titulo,
        @NotNull(message = "Valor da despesa obrigatório") BigDecimal valor,
        @NotNull(message = "Id da categoria obrigatório") Long idCategoria,
        @NotBlank(message = "Tipo da despesa obrigatório") String tipoDespesa,
        Integer dataVencimento,
        @Min(value = 1, message = "Parcelas totais devem ser maior que zero")
        @NotNull(message = "Parcelas totais obrigatório")
        Integer parcelasTotais
) {
}
