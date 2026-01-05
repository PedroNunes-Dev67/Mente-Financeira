package PedroNunesDev.MenteFinanceira.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagamento_despesa")
public class Pagamento_Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate diaPagamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_despesa")
    private Despesa despesa;

    public Pagamento_Despesa() {
    }

    public Pagamento_Despesa(LocalDate diaPagamento, Despesa despesa) {
        this.diaPagamento = diaPagamento;
        this.despesa = despesa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(LocalDate diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }
}
