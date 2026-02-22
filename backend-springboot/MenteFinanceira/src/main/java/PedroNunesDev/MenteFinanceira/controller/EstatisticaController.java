package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.response.EstatisticaDtoResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.EstatisticaService;
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
@RequestMapping("/estatisticas")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@Tag(name="Estatistica Controller", description = "Responsável por acessar as estatisticas do usuário")
public class EstatisticaController {

    @Autowired
    private EstatisticaService estatisticaService;

    @GetMapping
    public ResponseEntity<EstatisticaDtoResponse> findEstatisticas(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal){

        EstatisticaDtoResponse estatistica = estatisticaService.buscarEstatisticas(dataInicial,dataFinal);

        return ResponseEntity.ok(estatistica);
    }
}
