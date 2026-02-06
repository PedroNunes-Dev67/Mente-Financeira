package PedroNunesDev.MenteFinanceira.model;

import PedroNunesDev.MenteFinanceira.dto.request.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "despesa")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Despesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesa",nullable = false)
    private Long idDespesa;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDespesa tipoDespesa;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DespesaStatus despesaStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_criacao_despesa",nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao_despesa")
    private LocalDateTime dataAtualizacao;

    @Column(name = "parcelas_despesas")
    private Integer parcelas;

    @JsonIgnore
    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PagamentoDespesa> pagamentos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public Despesa(DespesaDTORequest despesaDTORequest,Usuario usuario, Categoria categoria) {
        this.titulo = despesaDTORequest.titulo();
        this.valor = despesaDTORequest.valor();
        this.tipoDespesa = TipoDespesa.valueOf(despesaDTORequest.tipoDespesa());
        this.despesaStatus = DespesaStatus.PENDENTE;
        this.usuario = usuario;
        this.dataPagamento = despesaDTORequest.dataPagamento();
        this.dataVencimento = despesaDTORequest.dataVencimento();
        this.dataCriacao = LocalDateTime.now();
        this.parcelas = despesaDTORequest.parcelas();
        this.categoria = categoria;
    }

    @JsonIgnore
    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isPaga(){

        return this.despesaStatus == DespesaStatus.PAGO;
    }

    public void marcarComoPaga(LocalDate dataPagamento){

        if (!isPaga()) {
            this.despesaStatus = DespesaStatus.PAGO;
            this.dataPagamento = dataPagamento;

            if (parcelas != null && parcelas > 0){
                parcelas -= 1;
            }
        }
    }

    public void renovarDespesa(){

        if (isPaga() && this.parcelas >= 0){

            this.despesaStatus = DespesaStatus.PENDENTE;
        }
    }
}
