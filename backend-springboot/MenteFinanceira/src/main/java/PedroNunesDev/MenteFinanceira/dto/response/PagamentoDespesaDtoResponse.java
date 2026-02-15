package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.enums.TipoPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDespesaDtoResponse(
        Long idPagamento,
        LocalDate diaPagamento,
        TipoPagamento tipoPagamento,
        Long id_despesa,
        String titulo_despesa,
        BigDecimal valor_despesa,
        Integer parcelasPagas,
        Integer parcelasTotais
) {
}
