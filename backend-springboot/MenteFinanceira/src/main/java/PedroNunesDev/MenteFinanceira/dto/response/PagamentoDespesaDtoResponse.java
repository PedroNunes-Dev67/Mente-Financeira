package PedroNunesDev.MenteFinanceira.dto.response;

import java.time.LocalDate;

public record PagamentoDespesaDtoResponse(
        Long idPagamento,
        LocalDate diaPagamento,
        DespesaDtoResponse despesaDtoResponse
) {
}
