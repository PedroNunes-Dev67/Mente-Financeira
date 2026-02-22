package PedroNunesDev.MenteFinanceira.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaDtoResponse {

    private Long despesasTotaisPeriodo;
    private Long despesasRecorrentes;
    private Long despesasNaoRecorrentes;
    private BigDecimal totalPago;
    private BigDecimal totalPendente;

    public EstatisticaDtoResponse(Long despesasTotaisPeriodo, Long despesasRecorrentes, Long despesasNaoRecorrentes) {
        this.despesasTotaisPeriodo = despesasTotaisPeriodo;
        this.despesasRecorrentes = despesasRecorrentes;
        this.despesasNaoRecorrentes = despesasNaoRecorrentes;
    }
}
