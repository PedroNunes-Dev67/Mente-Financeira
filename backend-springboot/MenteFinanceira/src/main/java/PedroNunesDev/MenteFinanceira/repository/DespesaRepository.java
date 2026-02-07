package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa,Long> {

    Optional<Despesa> findByIdDespesaAndUsuario(Long idDespesa, Usuario usuario);

    Page<Despesa> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria, Pageable pageable);

    Page<Despesa> findByUsuarioAndDespesaStatus(Usuario usuario, DespesaStatus despesaStatus, Pageable pageable);

    Page<Despesa> findByTipoDespesaAndUsuario(TipoDespesa tipoDespesa, Usuario usuario, Pageable pageable);

    Page<Despesa> findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa tipoDespesa, Usuario usuario, DespesaStatus despesaStatus, Pageable pageable);

    Page<Despesa> findByUsuario(Usuario usuario, Pageable pageable);
}
