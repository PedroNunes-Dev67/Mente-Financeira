package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.response.PagamentoDespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pagamento Controller", description = "Controlador de todas as funções relacionadas aos pagamentos das despesas")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Operation(summary = "Listar todos os pagamentos do usuário",
            description = "Busca pagamentos relacioandos ao usuário com paginação" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping("/me")
    public ResponseEntity<Page<PagamentoDespesaDtoResponse>> todosPagamentosUsuario(@RequestParam int pagina,@RequestParam int items){

        Page<PagamentoDespesaDtoResponse> paginacao = pagamentoService.todosPagamentosUsuario(pagina, items);

        return ResponseEntity.ok(paginacao);
    }

    @Operation(summary = "Realizar pagamento de uma despesa",
            description = "Busca despesa por Id e realiza o pagamento" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @PostMapping("/{id}")
    public ResponseEntity<PagamentoDespesaDtoResponse> pagamentoDespesa(@PathVariable Long id, @RequestParam String tipoPagamento){

        PagamentoDespesaDtoResponse pagamento = pagamentoService.pagamentoDespesa(id, tipoPagamento);

        return ResponseEntity.ok(pagamento);
    }

    @Operation(summary = "Deletar um pagamento",
            description = "Deleta um pagamento e faz rollback(atualiza despesa removando uma parcela paga)" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id){

        pagamentoService.deletarPagamento(id);

        return ResponseEntity.noContent().build();
    }
}
