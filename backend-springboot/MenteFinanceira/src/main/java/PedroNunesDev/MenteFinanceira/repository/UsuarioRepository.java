package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<UserDetails> findByEmail(String email);

    @Query("SELECT u FROM Usuario u" +
            " JOIN FETCH u.roles" +
            " WHERE u.email = :email")
    Optional<Usuario> buscarRolesDoUsuario(@Param("email") String email);
}
