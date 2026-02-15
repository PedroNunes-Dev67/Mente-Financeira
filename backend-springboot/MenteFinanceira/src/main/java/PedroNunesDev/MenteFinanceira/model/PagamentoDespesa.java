package PedroNunesDev.MenteFinanceira.model;

import PedroNunesDev.MenteFinanceira.model.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "pagamento_despesa")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PagamentoDespesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate diaPagamento;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_despesa")
    private Despesa despesa;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    public PagamentoDespesa(LocalDate diaPagamento, Despesa despesa, String tipoPagamento) {
        this.diaPagamento = diaPagamento;
        this.despesa = despesa;
        this.tipoPagamento = TipoPagamento.from(tipoPagamento);
    }
}
