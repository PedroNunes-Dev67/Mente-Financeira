package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.response.PagamentoDespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.exception.ConflitoRecursosException;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.mapper.PagamentoDespesaMapper;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PagamentoDespesaMapper pagamentoDespesaMapper;

    @Transactional(readOnly = true)
    public Page<PagamentoDespesaDtoResponse> todosPagamentosUsuario(int pagina, int items){

        Usuario usuario = authService.me();

        Page<PagamentoDespesa> paginacao = pagamentoRepository.findPagamentosByUsuario(usuario, criarPageable(pagina, items));

        return converterParaDTO(paginacao);
    }

    @Transactional
    public PagamentoDespesaDtoResponse pagamentoDespesa(Long id, String tipoPagamento){

        Usuario usuario = authService.me();

        Despesa despesa = findByDespesa(id, usuario);

        validaDespesa(despesa);

        PagamentoDespesa pagamento = salvarPagamento(despesa, tipoPagamento);

        PagamentoDespesaDtoResponse pagamentoDespesaDtoResponse = criacaoDeDTOs(pagamento);

        return pagamentoDespesaDtoResponse;
    }

    @Transactional
    public void deletarPagamento(Long idPagamento){

        Usuario usuario = authService.me();

        PagamentoDespesa pagamento = pagamentoRepository.findPagamentosByIdPagamentoAndUsuario(idPagamento, usuario);

        if (pagamento == null) throw new ResourceNotFoundException("Pagamento não encontrado");

        //Pego despesa relacionada ao pagamento
        Despesa despesa = pagamento.getDespesa();

        //Ao deletar um pagamento, o numero de parcelas pagas diminui 1
        rollBackDespesaParcelas(despesa);

        pagamentoRepository.delete(pagamento);
    }

    private PagamentoDespesa salvarPagamento(Despesa despesa, String tipoPagamento){

        LocalDate dataPagamento = LocalDate.now();

        despesa.registrarPagamento();

        despesaRepository.save(despesa);

        return pagamentoRepository.save(new PagamentoDespesa(dataPagamento, despesa, tipoPagamento));
    }

    private Despesa findByDespesa(Long id, Usuario usuario){

        return despesaRepository.findByIdDespesaAndUsuario(id, usuario).orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));
    }

    private void validaDespesa(Despesa despesa){

        if (despesa.isPaga()) throw new ConflitoRecursosException("O pagamento desta despesa já foi efetuado");
    }

    private PagamentoDespesaDtoResponse criacaoDeDTOs(PagamentoDespesa pagamento){

        PagamentoDespesaDtoResponse pagamentoDespesaDtoResponse = pagamentoDespesaMapper.toDTO(pagamento);

        return pagamentoDespesaDtoResponse;
    }

    private void rollBackDespesaParcelas(Despesa despesa){

        despesa.rollBackDespesa();
        despesaRepository.save(despesa);
    }

    //Métodos responsaveis por verificar parâmentros de páginação
    private int normalizaPagina(int pagina){

        //Página precisa ser maior ou igual a 0
        return Math.max(pagina,0);
    }

    private int normalizaTamanhoDePagina(int items){

        //Items de uma página devem ser maior que 0 e menor que 10
        return Math.min(Math.max(items,1),10);
    }

    private PageRequest criarPageable(int pagina, int items){

        //Faz normalização da pagina e items por página
        return PageRequest.of(normalizaPagina(pagina), normalizaTamanhoDePagina(items));
    }

    private Page<PagamentoDespesaDtoResponse> converterParaDTO(Page<PagamentoDespesa> paginacaoCriada){

        return paginacaoCriada
                .map(pagamento -> {

                    return pagamentoDespesaMapper.toDTO(pagamento);
                });
    }
}
