package PedroNunesDev.MenteFinanceira.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;


public record DespesaDTORequest(
        @NotBlank(message = "Titulo da despesa obrigatório") String titulo,
        @NotNull(message = "Valor da despesa obrigatório") BigDecimal valor,
        @NotNull(message = "Id da categoria obrigatório") Long idCategoria,
        @NotBlank(message = "Tipo da despesa obrigatório") String tipoDespesa,
        @NotNull Integer dataVencimento,
        Integer parcelasTotais,
        Integer parcelasPagas
) {
}
