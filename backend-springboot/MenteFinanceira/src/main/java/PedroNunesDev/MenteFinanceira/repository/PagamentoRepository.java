package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento_Despesa,Long> {

    @Query("SELECT p FROM Pagamento_Despesa p join p.despesa d where d.usuario = :usuario and d.despesaStatus = :status")
    List<Pagamento_Despesa> findPagamentosByUsuarioAndStatusDespesa(@Param("usuario") Usuario usuario,@Param("status") DespesaStatus despesaStatus);
}
