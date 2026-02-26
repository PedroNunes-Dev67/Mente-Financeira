package PedroNunesDev.MenteFinanceira.dto.build;

import PedroNunesDev.MenteFinanceira.model.Despesa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioMensalDtoBuild {

    private Set<Despesa> listaDeDespesasPagas = new HashSet<>();
    private BigDecimal totalDePagamento;

    public RelatorioMensalDtoBuild(Despesa despesa, BigDecimal totalDePagamento) {
        this.listaDeDespesasPagas.add(despesa);
        this.totalDePagamento = totalDePagamento;
    }

    public RelatorioMensalDtoBuild(BigDecimal totalDePagamento) {
        this.totalDePagamento = totalDePagamento;
    }
}
