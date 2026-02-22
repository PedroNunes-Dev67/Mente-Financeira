package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.response.EstatisticaDtoResponse;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class EstatisticaService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private AuthService authService;

    /*public EstatisticaDtoResponse buscarEstatisticas(LocalDate dataInicial, LocalDate dataFinal){

        Usuario usuario = authService.me();

        if (dataInicial.isAfter(dataFinal)) throw new IllegalArgumentException("Data inicial não pode ser depois da data final");

        boolean verificadorDeMeses = ChronoUnit.MONTHS.between(dataInicial,dataFinal) > 3;   //Verifica se o intervalo de datas é maior que 3 meses

        if (verificadorDeMeses) throw new IllegalArgumentException("Intervalo de datas não pode ser maior que 3 meses");

        BigDecimal valorTotalDePagamentosPorPeriodo = pagamentoRepository.valorTotalDePagamentosPorPeriodo(dataInicial,dataFinal,usuario);


    }*/
}
