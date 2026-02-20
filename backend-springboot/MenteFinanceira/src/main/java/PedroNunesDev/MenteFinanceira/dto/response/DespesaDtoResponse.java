package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DespesaDtoResponse (

     @Schema(example = "2345")
     Long idDespesa,
     @Schema(example = "Faculdade")
     String titulo,
     @Schema(example = "500.99")
     BigDecimal valor,
     @Schema(example = "RECORRENTE")
     TipoDespesa tipoDespesa,
     @Schema(example = "PENDENTE")
     DespesaStatus despesaStatus,
     @Schema(example = "5")
     Integer dataVencimento,
     @Schema(example = "2026-02-20T20:35:00")
     LocalDateTime dataCriacao,
     @Schema(example = "2026-02-20T20:35:00")
     LocalDateTime dataAtualizacao,
     @Schema(example = "12")
     Integer parcelasTotais,
     @Schema(example = "0")
     Integer parcelasPagas,
     UsuarioDTOResponse usuario,
     CategoriaDtoResponse categoria
)
{}



