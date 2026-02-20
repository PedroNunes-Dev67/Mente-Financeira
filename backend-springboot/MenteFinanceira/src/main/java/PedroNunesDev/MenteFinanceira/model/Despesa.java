package PedroNunesDev.MenteFinanceira.model;

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

    @Enumerated(EnumType.STRING)
    private DespesaStatus despesaStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;

    @Column(name = "data_vencimento")
    private Integer dataVencimento;

    @CreationTimestamp
    @Column(name = "data_criacao_despesa",nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao_despesa")
    private LocalDateTime dataAtualizacao;

    @Column(name = "parcelas_totais_despesa", nullable = false)
    private Integer parcelasTotais;

    @Column(name = "parcelas_pagas_despesa", nullable = false)
    private Integer parcelasPagas;

    @JsonIgnore
    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PagamentoDespesa> pagamentos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public Despesa(String titulo, BigDecimal valor, TipoDespesa tipoDespesa, Usuario usuario, Integer dataVencimento, Integer parcelasTotais, Categoria categoria) {
        this.titulo = titulo;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.usuario = usuario;
        this.dataVencimento = dataVencimento;
        this.parcelasTotais = parcelasTotais;
        this.parcelasPagas = 0;
        this.categoria = categoria;
        this.despesaStatus = DespesaStatus.PENDENTE;
    }

    public boolean isPaga(){

        return this.despesaStatus == DespesaStatus.PAGA;
    }

    public void marcarComoPaga(){

       if (isPaga()) return;

       this.despesaStatus = DespesaStatus.PAGA;
    }

    public void registrarPagamento(){

        if (this.parcelasPagas < this.parcelasTotais){
            this.parcelasPagas++;
        }

        analisarParcelas();
    }

    public void analisarParcelas(){

        if (this.parcelasPagas == 0){
            setDespesaStatus(DespesaStatus.PENDENTE);
        } else if (this.parcelasPagas > 0 && this.parcelasPagas < this.parcelasTotais) {
            setDespesaStatus(DespesaStatus.PARCIALMENTE_PAGA);
        } else if (this.parcelasPagas == this.parcelasTotais) {
            marcarComoPaga();
        }
    }

    public void rollBackDespesa(){

        if (parcelasPagas > 0){

            this.parcelasPagas--;
        }

        analisarParcelas();
    }
}
