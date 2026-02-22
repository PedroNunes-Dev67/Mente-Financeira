package PedroNunesDev.MenteFinanceira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaDtoResponse {

    private Long despesasTotaisPeriodo;
    private Long despesasRecorrentes;
    private Long despesasNaoRecorrentes;
    private BigDecimal totalPago;
    private BigDecimal totalGeral;
    private BigDecimal totalRestante;

    public EstatisticaDtoResponse(Long despesasTotaisPeriodo, Long despesasRecorrentes, Long despesasNaoRecorrentes, BigDecimal totalGeral) {
        this.despesasTotaisPeriodo = despesasTotaisPeriodo;
        this.despesasRecorrentes = despesasRecorrentes;
        this.despesasNaoRecorrentes = despesasNaoRecorrentes;
        this.totalGeral = totalGeral;
    }
}
