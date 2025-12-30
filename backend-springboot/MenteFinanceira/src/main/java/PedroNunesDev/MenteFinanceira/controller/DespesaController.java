package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @PostMapping
    public ResponseEntity<Despesa> cadastrarFinanca(@RequestBody @Valid DespesaDTORequest despesaDTORequest){

        Despesa novaDespesa = despesaService.cadastrarDespesa(despesaDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDespesa);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Despesa>> buscarTodasDespesasPorUsuario(){

        return ResponseEntity.ok(despesaService.buscarDespesasPorUsuario());
    }

    @PutMapping("/pagamento/{id}")
    public ResponseEntity<Pagamento_Despesa> pagamentoDespesa(@PathVariable Long id){

        Pagamento_Despesa pagamento = despesaService.pagamentoDespesa(id);

        return ResponseEntity.ok(pagamento);
    }

    @GetMapping("/me/categoria/{id}")
    public ResponseEntity<List<Despesa>> despesasPorCategoria(@PathVariable Long id){

        List<Despesa> list = despesaService.despesasPorCategoria(id);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/pendentes")
    public ResponseEntity<List<Despesa>> despesasPendetes(){

        List<Despesa> list = despesaService.despesasPendentes();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/pagas")
    public ResponseEntity<List<Despesa>> despesasPagas(){

        List<Despesa> list = despesaService.despesasPagas();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/recorrente")
    public ResponseEntity<List<Despesa>> despesasRecorrentes(){

        List<Despesa> list = despesaService.despesasRecorrentesUsuario();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/nao-recorrente")
    public ResponseEntity<List<Despesa>> despesasNaoRecorrentes(){

        List<Despesa> list = despesaService.despesasNaoRecorrentesUsuario();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/nao-recorrente/pagas")
    public ResponseEntity<List<Despesa>> despesasNaoRecorrentesPagas(){

        List<Despesa> list = despesaService.despesasNaoRecorrentesUsuarioPagas();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/nao-recorrente/pendentes")
    public ResponseEntity<List<Despesa>> despesasNaoRecorrentesPendentes(){

        List<Despesa> list = despesaService.despesasNaoRecorrentesUsuarioPendentes();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/recorrente/pendentes")
    public ResponseEntity<List<Despesa>> despesasRecorrentesPendentes(){

        List<Despesa> list = despesaService.despesasRecorrentesUsuarioPendentes();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/me/recorrente/pagas")
    public ResponseEntity<List<Despesa>> despesasRecorrentesPagas(){

        List<Despesa> list = despesaService.despesasRecorrentesUsuarioPagas();

        return ResponseEntity.ok(list);
    }
}
