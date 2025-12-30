package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public List<Pagamento_Despesa> todosPagamentosUsuario(){

        List<Despesa> list = despesaService.despesasPagas();

        List<Pagamento_Despesa> lista = new ArrayList<>();

        list.stream().map(des -> des.getPagamentos()).forEach(pag -> pag.forEach(p -> pagamento(lista, p)));

        return lista;
    }

    private void pagamento(List<Pagamento_Despesa> lista, Pagamento_Despesa pagamento_despesa){
        lista.add(pagamento_despesa);
    }

    private Usuario getUsuarioContext(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());
    }
}
