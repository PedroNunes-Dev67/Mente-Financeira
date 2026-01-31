package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;

import java.math.BigDecimal;
import java.util.List;

public record DespesaDtoResponse(
        Long idDespesa,
        String titulo,
        BigDecimal valor,
        TipoDespesa tipoDespesa,
        DespesaStatus despesaStatus,
        UsuarioDTOResponse usuario,
        CategoriaDtoResponse categoria,
        List<PagamentoDespesa> pagamentoDespesas
) {
}
