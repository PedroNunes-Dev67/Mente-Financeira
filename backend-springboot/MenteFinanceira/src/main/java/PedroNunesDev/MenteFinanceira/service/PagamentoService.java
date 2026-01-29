package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.exception.ConflitoRecursosException;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private AuthService authService;

    public List<Pagamento_Despesa> todosPagamentosUsuario(){

        Usuario usuario = authService.me();

        List<Pagamento_Despesa> listaDePagamentos = pagamentoRepository.findPagamentosByUsuarioAndStatusDespesa(usuario, DespesaStatus.PAGO);

        return listaDePagamentos;
    }

    public Pagamento_Despesa pagamentoDespesa(Long id){

        Usuario usuarioAuth = authService.me();

        Despesa despesa = despesaRepository.findByIdDespesaAndUsuario(id, usuarioAuth).orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));

        if (despesa.getDespesaStatus() == DespesaStatus.PAGO) throw new ConflitoRecursosException("O pagamento desta despesa já foi efetuado");

        Pagamento_Despesa pagamento = salvarPagamento(despesa);

        return pagamento;
    }

    @Transactional
    private Pagamento_Despesa salvarPagamento(Despesa despesa){

        despesa.setDespesaStatus(DespesaStatus.PAGO);

        despesaRepository.save(despesa);

        return pagamentoRepository.save(new Pagamento_Despesa(LocalDate.now(), despesa));
    }
}
