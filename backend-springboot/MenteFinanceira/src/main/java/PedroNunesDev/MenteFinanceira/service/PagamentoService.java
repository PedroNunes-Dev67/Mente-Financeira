package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.response.CategoriaDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.DespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.PagamentoDespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.exception.ConflitoRecursosException;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
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
    private DespesaRepository despesaRepository;
    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public List<PagamentoDespesaDtoResponse> todosPagamentosUsuario(){

        Usuario usuario = authService.me();

        List<PagamentoDespesa> listaDePagamentos = pagamentoRepository.findPagamentosByUsuario(usuario);

        return listaDePagamentos
                .stream()
                .map(pagamento -> {
                    return new PagamentoDespesaDtoResponse(pagamento.getId(), pagamento.getDiaPagamento(), pagamento.getTipoPagamento(),
                            new DespesaDtoResponse(
                                    pagamento.getDespesa(),
                                    new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail()),
                                    new CategoriaDtoResponse(pagamento.getDespesa().getCategoria())));
                }).toList();
    }

    @Transactional
    public PagamentoDespesaDtoResponse pagamentoDespesa(Long id, String tipoPagamento){

        Usuario usuario = authService.me();

        Despesa despesa = findByDespesa(id, usuario);

        validaDespesa(despesa);

        PagamentoDespesa pagamento = salvarPagamento(despesa, tipoPagamento);

        PagamentoDespesaDtoResponse pagamentoDespesaDtoResponse = criacaoDeDTOs(usuario,despesa,pagamento);

        return pagamentoDespesaDtoResponse;
    }


    public void verificarDespesaNaoRecorrente(Despesa despesa, String tipoPagamento){

        if (TipoDespesa.NAO_RECORRENTE.equals(despesa.getTipoDespesa())){

            pagamentoDespesa(despesa.getIdDespesa(), tipoPagamento);
        }
    }

    private PagamentoDespesa salvarPagamento(Despesa despesa, String tipoPagamento){

        LocalDate dataPagamento = analisarParcelasPagas(despesa);

        despesaRepository.save(despesa);

        return pagamentoRepository.save(new PagamentoDespesa(dataPagamento, despesa, tipoPagamento));
    }

    private LocalDate analisarParcelasPagas(Despesa despesa){

        LocalDate dataPagamento = LocalDate.now();

        if (despesa.getParcelasPagas() < despesa.getParcelasTotais()){
            despesa.setParcelasPagas(despesa.getParcelasPagas() + 1);
            if(despesa.getParcelasPagas() == despesa.getParcelasTotais()){
                despesa.marcarComoPaga(dataPagamento);
            }
        }
        return dataPagamento;
    }

    private Despesa findByDespesa(Long id, Usuario usuario){

        return despesaRepository.findByIdDespesaAndUsuario(id, usuario).orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));
    }

    private void validaDespesa(Despesa despesa){

        if (despesa.isPaga()) throw new ConflitoRecursosException("O pagamento desta despesa já foi efetuado");
    }

    private PagamentoDespesaDtoResponse criacaoDeDTOs(Usuario usuario, Despesa despesa, PagamentoDespesa pagamento){

        UsuarioDTOResponse usuarioDTOResponse = new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());

        CategoriaDtoResponse categoriaDtoResponse = new CategoriaDtoResponse(despesa.getCategoria());

        DespesaDtoResponse despesaDtoResponse = new DespesaDtoResponse(
                despesa,
                usuarioDTOResponse,
                categoriaDtoResponse);

        PagamentoDespesaDtoResponse pagamentoDespesaDtoResponse = new PagamentoDespesaDtoResponse(
                pagamento.getId(),
                pagamento.getDiaPagamento(),
                pagamento.getTipoPagamento(),
                despesaDtoResponse
        );

        return pagamentoDespesaDtoResponse;
    }
}
