package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagamentoRepository extends JpaRepository<PagamentoDespesa,Long> {

    @Query("SELECT p FROM PagamentoDespesa p join fetch p.despesa d where d.usuario = :usuario")
    Page<PagamentoDespesa> findPagamentosByUsuario(@Param("usuario") Usuario usuario, Pageable pageable);

    @Query("SELECT p FROM PagamentoDespesa p join fetch p.despesa d where d.usuario = :usuario and p.idPagamento = :idpagamento")
    PagamentoDespesa findPagamentosByIdPagamentoAndUsuario(@Param("idpagamento") Long idPagamento , @Param("usuario") Usuario usuario);
}
