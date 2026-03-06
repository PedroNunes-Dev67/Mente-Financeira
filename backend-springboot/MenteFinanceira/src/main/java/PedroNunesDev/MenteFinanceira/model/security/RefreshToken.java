package PedroNunesDev.MenteFinanceira.model.security;

import PedroNunesDev.MenteFinanceira.model.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_refresh_token")
    private Long id;

    @Column(unique = true,nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private Instant expiresAt;

    public RefreshToken(String token, Usuario usuario, Instant expiresAt) {
        this.token = token;
        this.usuario = usuario;
        this.expiresAt = expiresAt;
    }

    public boolean isExpire(){
        return expiresAt.isAfter(Instant.now());
    }
}
