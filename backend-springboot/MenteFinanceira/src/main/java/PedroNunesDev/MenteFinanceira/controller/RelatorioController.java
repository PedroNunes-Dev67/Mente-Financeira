package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.response.RelatorioMensalDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.RelatorioPorDataDtoResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name="Relatorio Controller", description = "Responsável por acessar as estatisticas do usuário")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@ApiResponse(responseCode = "403",description = "Erro de autenticação do usuário")
@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @Operation(summary = "Buscar relatorio de pagamento",
            description = "Realiza um relatório de pagamentos entre as datas passadas" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping("/pagamentos/periodo")
    public ResponseEntity<RelatorioPorDataDtoResponse> relatorioPorData(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal){

        RelatorioPorDataDtoResponse relatorio = relatorioService.relatorioPorData(dataInicial,dataFinal);

        return ResponseEntity.ok(relatorio);
    }

    @Operation(summary = "Gerar relatório mensal",
            description = "Realiza a geração de um relatório mensal das despesas e pagamentos" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping
    public ResponseEntity<RelatorioMensalDtoResponse> relatorioMensal(){

        RelatorioMensalDtoResponse relatorio = relatorioService.relatorioMensal();

        return ResponseEntity.ok(relatorio);
    }
}
