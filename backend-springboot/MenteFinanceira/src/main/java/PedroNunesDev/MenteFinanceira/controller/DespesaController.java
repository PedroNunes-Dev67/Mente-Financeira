package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.DespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.DespesaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Despesa Controller",description = "Controlador de todas as funções relacionadas as despesas")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @PostMapping
    public ResponseEntity<DespesaDtoResponse> cadastrarFinanca(@RequestBody @Valid DespesaDTORequest despesaDTORequest){

        DespesaDtoResponse novaDespesa = despesaService.cadastrarDespesa(despesaDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDespesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id){

        despesaService.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Page<DespesaDtoResponse>> buscarTodasDespesasPorUsuario(@RequestParam int pagina, @RequestParam int items){

        return ResponseEntity.ok(despesaService.buscarDespesasPorUsuario(pagina, items));
    }

    @GetMapping("/me/categoria/{id}")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasPorCategoria(@PathVariable Long id, @RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao = despesaService.despesasPorCategoria(id,pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/pendentes")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasPendetes(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao = despesaService.despesasPendentes(pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/pagas")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasPagas(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasPagas(pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/recorrente")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasRecorrentes(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasRecorrentesUsuario(pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/nao-recorrente")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasNaoRecorrentes(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasNaoRecorrentesUsuario(pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/nao-recorrente/pagas")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasNaoRecorrentesPagas(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao = despesaService.despesasNaoRecorrentesUsuarioPagas(pagina,items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/nao-recorrente/pendentes")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasNaoRecorrentesPendentes(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasNaoRecorrentesUsuarioPendentes(pagina,items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/recorrente/pendentes")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasRecorrentesPendentes(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasRecorrentesUsuarioPendentes(pagina,items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/recorrente/pagas")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasRecorrentesPagas(@RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasRecorrentesUsuarioPagas(pagina,items);

        return ResponseEntity.ok(paginacao);
    }
}
