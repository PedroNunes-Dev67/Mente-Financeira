package PedroNunesDev.MenteFinanceira.model;

import PedroNunesDev.MenteFinanceira.model.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @Column(name = "id_pagamento")
    private Long id;
    private LocalDate diaPagamento;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "despesa")
    private Despesa despesa;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    //Campos para histórico
    @Column(nullable = false, name = "id_despesa")
    private Long idDespesa;

    @Column(nullable = false, name = "titulo_despesa")
    private String titulo;

    @Column(nullable = false, name = "valor_pagamento_despesa")
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer parcelasPagas;

    @Column(nullable = false)
    private Integer parcelasTotais;

    public PagamentoDespesa(LocalDate diaPagamento, Despesa despesa, String tipoPagamento) {
        this.diaPagamento = diaPagamento;
        this.despesa = despesa;
        this.tipoPagamento = TipoPagamento.from(tipoPagamento);
        this.idDespesa = despesa.getIdDespesa();
        this.titulo = despesa.getTitulo();
        this.valor = despesa.getValor();
        this.parcelasPagas = despesa.getParcelasPagas();
        this.parcelasTotais = despesa.getParcelasTotais();
    }
}
