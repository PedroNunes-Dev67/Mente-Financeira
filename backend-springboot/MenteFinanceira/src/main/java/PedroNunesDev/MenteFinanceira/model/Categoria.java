package PedroNunesDev.MenteFinanceira.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;
    @Column(name = "nome_categoria")
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private Set<Despesa> despesas = new HashSet<>();

    public Categoria(String nome) {
        this.nome = nome;
    }

    @JsonIgnore
    public Set<Despesa> getDespesas() {
        return despesas;
    }
}
