package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;

import java.math.BigDecimal;

public record DespesaDtoResponse
        (Long idDespesa,
         String titulo,
         BigDecimal valor,
         TipoDespesa tipoDespesa,
         DespesaStatus despesaStatus,
         UsuarioDTOResponse usuario,
        CategoriaDtoResponse categoria)
{}



