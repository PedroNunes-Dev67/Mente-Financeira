package PedroNunesDev.MenteFinanceira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioMensalDtoResponse {

   private Long despesasTotaisAtivas; //DespesaRepo
   private Long despesasPagas;  //PagamentoRepo
   private Long despesasNaoPagas; //DespesaRepo + PagamentoRepo
   private BigDecimal totalDePagamento; //PagamentoRepo
   private BigDecimal totalDePagamentoRestante; //Logica
   private LocalDate mesAnalisado; //Metodo

    public RelatorioMensalDtoResponse(Long despesasPagas, BigDecimal totalDePagamento) {
        this.despesasPagas = despesasPagas;
        this.totalDePagamento = totalDePagamento;
    }
}
