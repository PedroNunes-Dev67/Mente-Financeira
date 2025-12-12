package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.FinancaDTORequest;
import PedroNunesDev.MenteFinanceira.model.Financa;
import PedroNunesDev.MenteFinanceira.service.FinancaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financas")
public class FinancaController {

    @Autowired
    private FinancaService financaService;

    @PostMapping
    public ResponseEntity<Financa> cadastrarFinanca(@RequestBody @Valid FinancaDTORequest financaDTORequest){

        Financa novaFinanca = financaService.cadastrarFinanca(financaDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaFinanca);
    }

    @GetMapping
    public ResponseEntity<List<Financa>> buscarTodasFinancas(){

        return ResponseEntity.ok(financaService.buscandoFinancas());
    }
}
