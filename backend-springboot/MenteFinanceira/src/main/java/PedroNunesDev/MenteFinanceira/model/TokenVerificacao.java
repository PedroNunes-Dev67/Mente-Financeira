package PedroNunesDev.MenteFinanceira.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TokenVerificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean ativo;
    private LocalDateTime duracao;

    private Long idUsuario;

    public TokenVerificacao(String token, boolean ativo, LocalDateTime duracao, Long id) {
        this.token = token;
        this.ativo = ativo;
        this.duracao = duracao;
        this.idUsuario = id;
    }
}
