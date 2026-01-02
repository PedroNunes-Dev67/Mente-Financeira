package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping("/me")
    public ResponseEntity<List<Pagamento_Despesa>> todosPagamentosUsuario(){

        List<Pagamento_Despesa> list = pagamentoService.todosPagamentosUsuario();

        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento_Despesa> pagamentoDespesa(@PathVariable Long id){

        Pagamento_Despesa pagamento = pagamentoService.pagamentoDespesa(id);

        return ResponseEntity.ok(pagamento);
    }
}
