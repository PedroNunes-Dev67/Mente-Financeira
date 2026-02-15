package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.enums.TipoPagamento;

import java.time.LocalDate;

public record PagamentoDespesaDtoResponse(
        Long idPagamento,
        LocalDate diaPagamento,
        TipoPagamento tipoPagamento,
        DespesaDtoResponse despesaDtoResponse
) {
}
