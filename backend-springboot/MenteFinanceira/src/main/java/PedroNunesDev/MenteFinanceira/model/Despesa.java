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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(name = "data_criacao_despesa",nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao_despesa")
    private LocalDateTime dataAtualizacao;

    @Column(name = "parcelas_despesas")
    private Integer parcelas;

    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PagamentoDespesa> pagamentos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public Despesa(String titulo, BigDecimal valor, TipoDespesa tipoDespesa, Usuario usuario, LocalDate dataPagamento, LocalDate dataVencimento, Integer parcelas, Categoria categoria) {
        this.titulo = titulo;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.despesaStatus = DespesaStatus.PENDENTE;
        this.usuario = usuario;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.parcelas = parcelas;
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isPaga(){

        return this.despesaStatus == DespesaStatus.PAGO;
    }

    public void marcarComoPaga(LocalDate dataPagamento){

       if (isPaga()) return;

       this.despesaStatus = DespesaStatus.PAGO;
       this.dataPagamento = dataPagamento;

       if (temParcelasRestantes()){
           parcelas--;
       }
    }

    public void renovarDespesa(){

        if (isPaga() && temParcelasRestantes()){

            this.despesaStatus = DespesaStatus.PENDENTE;
        }
    }

    public boolean temParcelasRestantes(){
        return parcelas != null && parcelas > 0;
    }
}
