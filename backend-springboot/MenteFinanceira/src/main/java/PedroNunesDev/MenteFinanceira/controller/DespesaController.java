package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.DespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Despesa Controller",description = "Controlador de todas as funções relacionadas as despesas")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@ApiResponse(responseCode = "403",description = "Erro de autenticação do usuário")
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @Operation(summary = "Cadastrar despesa",
            description = "Realiza o cadastro de uma nova despesa" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @PostMapping
    public ResponseEntity<DespesaDtoResponse> cadastrarFinanca(@RequestBody @Valid DespesaDTORequest despesaDTORequest){

        DespesaDtoResponse novaDespesa = despesaService.cadastrarDespesa(despesaDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDespesa);
    }

    @Operation(summary = "Deletar despesa",
            description = "Deleta uma despesa do usuário" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id){

        despesaService.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar despesa",
            description = "Realiza a atualização de uma despesa do usuário" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @PutMapping("/{id}")
    public ResponseEntity<DespesaDtoResponse> atualizarDespesa(@PathVariable Long id, @RequestBody DespesaDTORequest despesaDTORequest){

        DespesaDtoResponse despesaAtualizada = despesaService.atualizarDespesa(id, despesaDTORequest);

        return ResponseEntity.ok(despesaAtualizada);
    }

    @Operation(summary = "Buscar todas as depesas do usuário",
            description = "Realiza a busca das despesas do usuario com paginação" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping("/me")
    public ResponseEntity<Page<DespesaDtoResponse>> buscarTodasDespesasDoUsuario(@RequestParam int pagina, @RequestParam int items){

        return ResponseEntity.ok(despesaService.buscarDespesasPorUsuario(pagina, items));
    }

    @GetMapping("/me/categoria/{id}")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasPorCategoria(@PathVariable Long id, @RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao = despesaService.despesasPorCategoria(id,pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/tipo")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasPorTipo(@RequestParam String tipoDespesa, @RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> paginacao  = despesaService.despesasPorTipo(tipoDespesa, pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/tipo/status")
    public ResponseEntity<Page<DespesaDtoResponse>> despesasPorTipoEhStatus(@RequestParam String tipoDespesa, @RequestParam String statusDespesa, @RequestParam int pagina, @RequestParam int items) {

        Page<DespesaDtoResponse> paginacao = despesaService.despesasPorTipoEhStatus(tipoDespesa,statusDespesa, pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/me/data")
    public ResponseEntity<Page<DespesaDtoResponse>> buscarDespesasPorData(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal, @RequestParam int pagina, @RequestParam int items){

        Page<DespesaDtoResponse> despesas = despesaService.despesasPorData(dataInicial,dataFinal,pagina,items);

        return ResponseEntity.ok(despesas);
    }
}
