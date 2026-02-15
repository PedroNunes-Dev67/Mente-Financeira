package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<PagamentoDespesa,Long> {

    @Query("SELECT p FROM PagamentoDespesa p join fetch p.despesa d where d.usuario = :usuario")
    List<PagamentoDespesa> findPagamentosByUsuario(@Param("usuario") Usuario usuario);
}
