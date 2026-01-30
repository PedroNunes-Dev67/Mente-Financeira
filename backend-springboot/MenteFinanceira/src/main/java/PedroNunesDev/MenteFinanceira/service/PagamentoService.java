package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Autowired
    private AuthService authService;

    public List<PagamentoDespesa> todosPagamentosUsuario(){

        Usuario usuario = authService.me();

        List<PagamentoDespesa> listaDePagamentos = pagamentoRepository.findPagamentosByUsuarioAndStatusDespesa(usuario, DespesaStatus.PAGO);

        return listaDePagamentos;
    }

    public PagamentoDespesa pagamentoDespesa(Long id){

        Usuario usuarioAuth = authService.me();

        Despesa despesa = despesaRepository.findByIdDespesaAndUsuario(id, usuarioAuth).orElseThrow(() -> new RuntimeException());

        if (despesa.getDespesaStatus() == DespesaStatus.PAGO) throw new RuntimeException();

        PagamentoDespesa pagamento = salvarPagamento(despesa);

        return pagamento;
    }

    @Transactional
    private PagamentoDespesa salvarPagamento(Despesa despesa){

        despesa.setDespesaStatus(DespesaStatus.PAGO);

        despesaRepository.save(despesa);

        return pagamentoRepository.save(new PagamentoDespesa(LocalDate.now(), despesa));
    }
}
