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

    @GetMapping("/me")
    public ResponseEntity<List<Financa>> buscarTodasFinancasPorUsuario(){

        return ResponseEntity.ok(financaService.buscarFinancasPorUsuario());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Financa> pagamentoFinanca(@PathVariable Long id){

        Financa financa = financaService.pagamentoFinanca(id);

        return ResponseEntity.ok(financa);
    }

    @GetMapping("/me/categoria/{id}")
    public ResponseEntity<List<Financa>> financasPorCategoria(@PathVariable Long id){

        List<Financa> list = financaService.financaPorCategoria(id);

        return ResponseEntity.ok(list);
    }
}
