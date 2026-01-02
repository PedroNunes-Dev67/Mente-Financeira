package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DespesaService despesaService;
    @Autowired
    private DespesaRepository despesaRepository;

    public List<Pagamento_Despesa> todosPagamentosUsuario(){

        //Pego a lista de despesas do usuário
        List<Despesa> listaDeDespesas = despesaService.despesasPagas();

        List<Pagamento_Despesa> listaDePagamentos = new ArrayList<>();

        //Percorro esta lista de despesas pegando outra lista de pagamentos, percorrendo ela e chamando um método para adicionar a lista de pagamentos
        listaDeDespesas.stream().map(des -> des.getPagamentos()).forEach(pag -> pag.forEach(p -> pagamento(listaDePagamentos, p)));

        return listaDePagamentos;
    }

    private void pagamento(List<Pagamento_Despesa> lista, Pagamento_Despesa pagamento_despesa){
        lista.add(pagamento_despesa);
    }

    public Pagamento_Despesa pagamentoDespesa(Long id){

        Usuario usuarioAuth = getUsuarioContext();

        Despesa despesa = despesaRepository.findByIdDespesaAndUsuario(id, usuarioAuth).orElseThrow(() -> new RuntimeException());

        if (despesa.getDespesaStatus() == DespesaStatus.PAGO) throw new RuntimeException();

        Pagamento_Despesa pagamento = salvarPagamento(despesa);

        return pagamento;
    }

    private Pagamento_Despesa salvarPagamento(Despesa despesa){

        despesa.setDespesaStatus(DespesaStatus.PAGO);

        despesaRepository.save(despesa);

        return pagamentoRepository.save(new Pagamento_Despesa(LocalDate.now(), despesa));
    }

    private Usuario getUsuarioContext(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());
    }
}
