package PedroNunesDev.MenteFinanceira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioPorDataDtoResponse {

    private Long despesasTotaisPagasPeriodo;
    private Long despesasRecorrentesPagas;
    private Long despesasNaoRecorrentesPagas;
    private BigDecimal valorPagoDeRecorrentes;
    private BigDecimal valorPagoDeNaoRecorrentes;
    private BigDecimal totalPago;
    private Set<Integer> anosAnalisados;
    private Set<String> mesesAnalisados;
    private LocalDate momentoDaAnalise;

    public RelatorioPorDataDtoResponse(Long despesasTotaisPeriodo, BigDecimal valorPagoDeRecorrentes,BigDecimal valorPagoDeNaoRecorrentes, BigDecimal totalPago) {
        this.despesasTotaisPagasPeriodo = despesasTotaisPeriodo;
        this.valorPagoDeRecorrentes = valorPagoDeRecorrentes;
        this.valorPagoDeNaoRecorrentes = valorPagoDeNaoRecorrentes;
        this.totalPago = totalPago;
    }
}
