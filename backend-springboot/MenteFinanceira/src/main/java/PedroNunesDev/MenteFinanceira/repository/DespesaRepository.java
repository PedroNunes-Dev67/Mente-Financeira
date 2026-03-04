package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface DespesaRepository extends JpaRepository<Despesa,Long> {

    Optional<Despesa> findByIdDespesaAndUsuario(Long idDespesa, Usuario usuario);

    Page<Despesa> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria, Pageable pageable);

    Page<Despesa> findByUsuarioAndDespesaStatus(Usuario usuario, DespesaStatus despesaStatus, Pageable pageable);

    Page<Despesa> findByTipoDespesaAndUsuario(TipoDespesa tipoDespesa, Usuario usuario, Pageable pageable);

    Page<Despesa> findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa tipoDespesa, Usuario usuario, DespesaStatus despesaStatus, Pageable pageable);

    @Query("SELECT d FROM Despesa d WHERE d.usuario = :usuario AND d.dataCriacao BETWEEN :dataInicial AND :dataFinal")
    Page<Despesa> findByDespesasPorData(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate datafinal, @Param("usuario") Usuario usuario, Pageable pageable);

    Page<Despesa> findByUsuario(Usuario usuario, Pageable pageable);

    @Query("SELECT " +
            " d" +
            " FROM Despesa d" +
            " WHERE d.usuario = :usuario AND" +
            " (d.despesaStatus = PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus.PARCIALMENTE_PAGA" +
            " OR" +
            " d.despesaStatus = PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus.PENDENTE)")
    Set<Despesa> buscarDespesasQueNaoForamQuitadas(@Param("usuario") Usuario usuario);

    @Query("SELECT COUNT(d) FROM Despesa d WHERE d.usuario = :usuario AND" +
            " d.despesaStatus " +
            " IN" +
            " (PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus.PENDENTE, PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus.PARCIALMENTE_PAGA)")
    Long buscarDespesasAtivas(@Param("usuario") Usuario usuario);
}
