package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.response.RelatorioMensalDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.RelatorioPorDataDtoResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.RelatorioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorio")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@Tag(name="Relatorio Controller", description = "Responsável por acessar as estatisticas do usuário")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/pagamentos/periodo")
    public ResponseEntity<RelatorioPorDataDtoResponse> findEstatisticas(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal){

        RelatorioPorDataDtoResponse relatorio = relatorioService.relatorioPorData(dataInicial,dataFinal);

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping
    public ResponseEntity<RelatorioMensalDtoResponse> relatorioMensal(){

        RelatorioMensalDtoResponse relatorio = relatorioService.relatorioMensal();

        return ResponseEntity.ok(relatorio);
    }
}
