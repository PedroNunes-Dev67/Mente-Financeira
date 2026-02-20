package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.enums.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDespesaDtoResponse(
        @Schema(example = "1234")
        Long idPagamento,
        @Schema(example = "2026-02-20")
        LocalDate diaPagamento,
        @Schema(example = "PIX")
        TipoPagamento tipoPagamento,
        @Schema(example = "2481")
        Long idDespesa,
        @Schema(example = "Faculdade")
        String titulo_despesa,
        @Schema(example = "500.99")
        BigDecimal valor_despesa,
        @Schema(example = "0")
        Integer parcelasPagas,
        @Schema(example = "12")
        Integer parcelasTotais
) {
}
