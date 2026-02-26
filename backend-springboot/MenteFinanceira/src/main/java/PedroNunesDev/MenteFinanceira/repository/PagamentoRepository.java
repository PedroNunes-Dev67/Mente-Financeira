package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.dto.build.RelatorioMensalDtoBuild;
import PedroNunesDev.MenteFinanceira.dto.response.RelatorioPorDataDtoResponse;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface PagamentoRepository extends JpaRepository<PagamentoDespesa,Long> {

    @Query("SELECT p FROM PagamentoDespesa p join fetch p.despesa d where d.usuario = :usuario")
    Page<PagamentoDespesa> findPagamentosByUsuario(@Param("usuario") Usuario usuario, Pageable pageable);

    @Query("SELECT p FROM PagamentoDespesa p join fetch p.despesa d where d.usuario = :usuario and p.idPagamento = :idpagamento")
    PagamentoDespesa findPagamentosByIdPagamentoAndUsuario(@Param("idpagamento") Long idPagamento , @Param("usuario") Usuario usuario);

    @Query("SELECT new PedroNunesDev.MenteFinanceira.dto.response.RelatorioPorDataDtoResponse(" +
            " COALESCE(COUNT(DISTINCT d.idDespesa),0)," +
            " COALESCE(SUM(CASE WHEN d.tipoDespesa = 'RECORRENTE' THEN d.valor ELSE 0 END), 0)," +
            " COALESCE(SUM(CASE WHEN d.tipoDespesa = 'NAO_RECORRENTE' THEN d.valor ELSE 0 END), 0)," +
            " COALESCE(SUM(p.valor_despesa),0)" +
            ")"+
            " FROM PagamentoDespesa p "+
            " JOIN p.despesa d" +
            " WHERE d.usuario = :usuario"+
            " AND p.diaPagamento BETWEEN :dataInicial AND :dataFinal")
    RelatorioPorDataDtoResponse buscarRelatorioPorData
            (@Param("dataInicial") LocalDate dataInicial,
             @Param("dataFinal") LocalDate dataFinal,
             @Param("usuario") Usuario usuario);

    @Query("SELECT d FROM PagamentoDespesa p" +
            " JOIN p.despesa d" +
            " WHERE d.usuario = :usuario" +
            " AND" +
            " p.diaPagamento BETWEEN :dataInicial AND :dataFinal")
    Set<Despesa> buscarDespesasPorData
            (@Param("dataInicial") LocalDate dataInicial,
             @Param("dataFinal") LocalDate dataFinal,
             @Param("usuario") Usuario usuario);

    @Query("SELECT new PedroNunesDev.MenteFinanceira.dto.build.RelatorioMensalDtoBuild(" +
            " d," +
            " COALESCE(SUM(p.valor_despesa),0   )" +
            ")" +
            " FROM PagamentoDespesa p" +
            " JOIN p.despesa d" +
            " WHERE d.usuario = :usuario" +
            " AND p.diaPagamento BETWEEN :dataInicial AND :dataFinal" +
            " GROUP BY d")
    RelatorioMensalDtoBuild relatorioMensalBuild(
            @Param("usuario") Usuario usuario,
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal
    );
}
