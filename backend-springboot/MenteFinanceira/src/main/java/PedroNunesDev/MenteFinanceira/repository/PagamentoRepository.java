package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<PagamentoDespesa,Long> {

<<<<<<< HEAD
    @Query("SELECT p FROM Pagamento_Despesa p join p.despesa d where d.usuario = :usuario and d.despesaStatus = :status")
    List<PagamentoDespesa> findPagamentosByUsuarioAndStatusDespesa(@Param("usuario") Usuario usuario, @Param("status") DespesaStatus despesaStatus);
=======
    @Query("SELECT p FROM Pagamento_Despesa p join fetch p.despesa d where d.usuario = :usuario and d.despesaStatus = :status")
    List<Pagamento_Despesa> findPagamentosByUsuarioAndStatusDespesa(@Param("usuario") Usuario usuario,@Param("status") DespesaStatus despesaStatus);
>>>>>>> 6bde7e0a0c96bdc2d1ad2b6b0ae3c9fb56f97b7c
}
