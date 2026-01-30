package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.PagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pagamento Controller", description = "Controlador de todas as funções relacionadas aos pagamentos das despesas")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping("/me")
    public ResponseEntity<List<PagamentoDespesa>> todosPagamentosUsuario(){

        List<PagamentoDespesa> list = pagamentoService.todosPagamentosUsuario();

        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDespesa> pagamentoDespesa(@PathVariable Long id){

        PagamentoDespesa pagamento = pagamentoService.pagamentoDespesa(id);

        return ResponseEntity.ok(pagamento);
    }
}
