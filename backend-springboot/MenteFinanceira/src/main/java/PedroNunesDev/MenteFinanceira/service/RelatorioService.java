package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.build.RelatorioMensalDtoBuild;
import PedroNunesDev.MenteFinanceira.dto.response.RelatorioMensalDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.RelatorioPorDataDtoResponse;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RelatorioService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private DespesaRepository despesaRepository;

    public RelatorioPorDataDtoResponse relatorioPorData(LocalDate dataInicial, LocalDate dataFinal){

        Usuario usuario = authService.me();

        if (dataInicial.isAfter(dataFinal)) throw new IllegalArgumentException("Data inicial não pode ser depois da data final");

        boolean verificadorDeMeses = ChronoUnit.MONTHS.between(dataInicial,dataFinal) > 3;   //Verifica se o intervalo de datas é maior que 3 meses

        if (verificadorDeMeses) throw new IllegalArgumentException("Intervalo de datas não pode ser maior que 3 meses");

        RelatorioPorDataDtoResponse relatorioPorData = pagamentoRepository.buscarRelatorioPorData(dataInicial, dataFinal, usuario);

        Set<Despesa> despesas = pagamentoRepository.buscarDespesasPorData(dataInicial,dataFinal,usuario);

        Long depesasRecorrentesPagas = 0L;
        Long despesasNaoRecorrentesPagas = 0L;
        for (Despesa d : despesas){
            if (d.getTipoDespesa().equals(TipoDespesa.RECORRENTE)){
                depesasRecorrentesPagas++;
            }
            else if(d.getTipoDespesa().equals(TipoDespesa.NAO_RECORRENTE)){
                despesasNaoRecorrentesPagas++;
            }
        }

        relatorioPorData.setDespesasRecorrentesPagas(depesasRecorrentesPagas);
        relatorioPorData.setDespesasNaoRecorrentesPagas(despesasNaoRecorrentesPagas);
        relatorioPorData.setAnosAnalisados(pegarAnosDaRequisicao(dataInicial,dataFinal));
        relatorioPorData.setMesesAnalisados(pegarMesesDaRequisicao(dataInicial,dataFinal));
        relatorioPorData.setMomentoDaAnalise(LocalDate.now());

        return relatorioPorData;
    }

    public RelatorioMensalDtoResponse relatorioMensal(){

        Usuario usuario = authService.me();

        LocalDate dataInicial = LocalDate.now().withDayOfMonth(1);

        int diaFinalDoMes =  dataInicial.lengthOfMonth();

        LocalDate dataFinal = LocalDate.now().withDayOfMonth(diaFinalDoMes);

        //Todas as despesas que tiveram pagamento no mes e a soma do valor pago
        RelatorioMensalDtoBuild relatorioBuild = pagamentoRepository.relatorioMensalBuild(usuario, dataInicial, dataFinal);

        if (relatorioBuild == null){
            relatorioBuild = new RelatorioMensalDtoBuild();
            relatorioBuild.setTotalDePagamento(BigDecimal.ZERO);
        }

        //Pego o total de despesas pagas no mes
        Long despesasPagas = (long) relatorioBuild.getListaDeDespesasPagas().size();

        Set<Despesa> despesasNaoPagas = despesaRepository.buscarDespesasNaoPagas(usuario);

        //Pego a quantidade de despesas ativas
        Long despesasAtivas = despesaRepository.buscarDespesasAtivas(usuario);

        //Removo e deixo apenas as despesas que não tiveram pagamentos no mes
        despesasNaoPagas.removeAll(relatorioBuild.getListaDeDespesasPagas());

        //Pego a quantidade de despesas não pagas
        Long quantidadeDeDespesasNaoPagas = (long) despesasNaoPagas.size();

        BigDecimal valorTotalRestante = BigDecimal.ZERO;

        //Pego os valores das despesas que não tiveram pagamento no mes
        for (Despesa d: despesasNaoPagas){
            if (d.getValor() != null) {
                valorTotalRestante = valorTotalRestante.add(d.getValor());
            }
        }

        LocalDate mesAnalisado = LocalDate.now();

        RelatorioMensalDtoResponse relatorio = new RelatorioMensalDtoResponse(
                despesasAtivas,
                despesasPagas,
                quantidadeDeDespesasNaoPagas,
                relatorioBuild.getTotalDePagamento(),
                valorTotalRestante,
                mesAnalisado
        );

        return relatorio;
    }


    //Pega a data inicial e a final de requisição e retorna os meses entre elas
    private List<String> pegarMesesDaRequisicao(LocalDate dataInicial, LocalDate dataFinal){

        List<String> listaDeMeses = new ArrayList<>();

        String[] meses = {"Janeiro","Fevereiro", "Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

        while(!dataInicial.isAfter(dataFinal)){

            int mes = dataInicial.getMonthValue();

            listaDeMeses.add(meses[mes-1]);
            dataInicial = dataInicial.plusMonths(1);
        }

        return listaDeMeses;
    }

    //Pega a data inicial e a final de requisição e retorna os anos entre elas
    private Set<Integer> pegarAnosDaRequisicao(LocalDate dataInicial, LocalDate dataFinal){

        Set<Integer> listaDeAnos = new HashSet<>();

        while(!dataInicial.isAfter(dataFinal)){

            listaDeAnos.add(dataInicial.getYear());
            dataInicial = dataInicial.plusMonths(1);
        }

        return listaDeAnos;
    }
}
