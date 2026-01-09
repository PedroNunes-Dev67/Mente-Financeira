package PedroNunesDev.MenteFinanceira.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TokenVerificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean ativo;
    private LocalDateTime duracao;

    private Long idUsuario;

    public TokenVerificacao() {
    }

    public TokenVerificacao(String token, boolean ativo, LocalDateTime duracao, Long id) {
        this.token = token;
        this.ativo = ativo;
        this.duracao = duracao;
        this.idUsuario = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalDateTime duracao) {
        this.duracao = duracao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenVerificacao that = (TokenVerificacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
