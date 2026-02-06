package PedroNunesDev.MenteFinanceira.model;

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
    private Long idDespesa;
    private String titulo;
    private BigDecimal valor;
    private TipoDespesa tipoDespesa;
    private DespesaStatus despesaStatus;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PagamentoDespesa> pagamentos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public Despesa(String titulo, BigDecimal valor, TipoDespesa tipoDespesa, DespesaStatus despesaStatus, Usuario usuario, Categoria categoria) {
        this.titulo = titulo;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.despesaStatus = despesaStatus;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    @JsonIgnore
    public Usuario getUsuario() {
        return usuario;
    }
}
