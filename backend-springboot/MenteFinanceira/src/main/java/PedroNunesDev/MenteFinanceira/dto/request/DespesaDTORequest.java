package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record DespesaDTORequest(
        @NotBlank(message = "Titulo da despesa obrigatório") String titulo,
        @NotNull(message = "Valor da despesa obrigatório") BigDecimal valor,
        @NotNull(message = "Dia de pagamento da despesa obrigatório") Integer diaDePagamento,
        @NotNull(message = "Id da categoria obrigatório") Long id_categoria,
        @NotBlank(message = "Tipo da despesa obrigatório") String tipoDespesa
) {
}
