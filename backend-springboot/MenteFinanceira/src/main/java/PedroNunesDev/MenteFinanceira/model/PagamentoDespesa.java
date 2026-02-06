package PedroNunesDev.MenteFinanceira.model;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_despesa")
    private Despesa despesa;

    public PagamentoDespesa(LocalDate diaPagamento, Despesa despesa) {
        this.diaPagamento = diaPagamento;
        this.despesa = despesa;
    }
}
