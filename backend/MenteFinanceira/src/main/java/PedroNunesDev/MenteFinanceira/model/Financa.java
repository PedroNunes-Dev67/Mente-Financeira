package PedroNunesDev.MenteFinanceira.model;

import PedroNunesDev.MenteFinanceira.model.enums.FinancaStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "financa")
public class Financa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_financa;
    private String titulo;
    private Integer valor;
    private FinancaStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public Financa() {
    }

    public Financa(String titulo, Integer valor, FinancaStatus status, Usuario usuario) {
        this.titulo = titulo;
        this.valor = valor;
        this.status = status;
        this.usuario = usuario;
    }

    public Long getId_financa() {
        return id_financa;
    }

    public void setId_financa(Long id_financa) {
        this.id_financa = id_financa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public FinancaStatus getStatus() {
        return status;
    }

    public void setStatus(FinancaStatus status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Financa financa = (Financa) o;
        return Objects.equals(id_financa, financa.id_financa);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_financa);
    }
}
