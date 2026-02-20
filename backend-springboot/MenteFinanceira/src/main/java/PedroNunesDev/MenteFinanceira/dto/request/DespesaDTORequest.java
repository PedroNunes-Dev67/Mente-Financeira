package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record DespesaDTORequest(
        @NotBlank(message = "Titulo da despesa obrigatório")
        @Schema(example = "Faculdade")
        String titulo,
        @NotNull(message = "Valor da despesa obrigatório")
        @Schema(example = "500.99")
        BigDecimal valor,
        @NotNull(message = "Id da categoria obrigatório")
        @Schema(example = "6 'Categoria de educação'")
        Long idCategoria,
        @NotBlank(message = "Tipo da despesa obrigatório")
        @Schema(example = "recorrente")
        String tipoDespesa,
        @Schema(example = "5")
        Integer dataVencimento,
        @Min(value = 1, message = "Parcelas totais devem ser maior que zero")
        @NotNull(message = "Parcelas totais obrigatório")
        @Schema(example = "12")
        Integer parcelasTotais
) {
}
