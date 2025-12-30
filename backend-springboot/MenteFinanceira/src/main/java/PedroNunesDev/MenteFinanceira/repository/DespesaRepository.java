package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa,Long> {

    Optional<Despesa> findByIdDespesaAndUsuario(Long idDespesa, Usuario usuario);

    List<Despesa> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria);

    List<Despesa> findByUsuarioAndDespesaStatus(Usuario usuario, DespesaStatus despesaStatus);

    List<Despesa> findByTipoDespesaAndUsuario(TipoDespesa tipoDespesa, Usuario usuario);

    List<Despesa> findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa tipoDespesa, Usuario usuario, DespesaStatus despesaStatus);
}
