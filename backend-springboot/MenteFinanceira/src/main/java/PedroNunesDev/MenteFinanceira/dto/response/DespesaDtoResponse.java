package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DespesaDtoResponse
        (Long idDespesa,
         String titulo,
         BigDecimal valor,
         TipoDespesa tipoDespesa,
         DespesaStatus despesaStatus,
         LocalDate dataVencimento,
         LocalDateTime dataCriacao,
         LocalDateTime dataAtualizacao,
         Integer parcelasTotais,
         Integer parcelasPagas,
         UsuarioDTOResponse usuario,
         CategoriaDtoResponse categoria)
{}



