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
    public List<PagamentoDespesa> todosPagamentosUsuario(){

        Usuario usuario = authService.me();

        List<PagamentoDespesa> listaDePagamentos = pagamentoRepository.findPagamentosByUsuarioAndStatusDespesa(usuario, DespesaStatus.PAGO);

        return listaDePagamentos;
    }

    @Transactional
    public PagamentoDespesaDtoResponse pagamentoDespesa(Long id){

        Usuario usuario = authService.me();

        Despesa despesa = findByDespesa(id, usuario);

        validaDespesa(despesa);

        PagamentoDespesa pagamento = salvarPagamento(despesa);

        PagamentoDespesaDtoResponse pagamentoDespesaDtoResponse = criacaoDeDTOs(usuario,despesa,pagamento);

        return pagamentoDespesaDtoResponse;
    }


    public void verificarDespesaNaoRecorrente(Despesa despesa){

        if (TipoDespesa.NAO_RECORRENTE.equals(despesa.getTipoDespesa())){

            pagamentoDespesa(despesa.getIdDespesa());
        }
    }

    private PagamentoDespesa salvarPagamento(Despesa despesa){

        LocalDate dataPagamento = LocalDate.now();

        despesa.marcarComoPaga(dataPagamento);

        despesaRepository.save(despesa);

        return pagamentoRepository.save(new PagamentoDespesa(dataPagamento, despesa));
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

        DespesaDtoResponse despesaDtoResponse = new DespesaDtoResponse(despesa.getIdDespesa(),
                despesa.getTitulo(),
                despesa.getValor(),
                despesa.getTipoDespesa(),
                despesa.getDespesaStatus(),
                despesa.getDataVencimento(),
                despesa.getDataCriacao(),
                despesa.getDataAtualizacao(),
                despesa.getParcelasTotais(),
                despesa.getParcelasPagas(),
                usuarioDTOResponse,
                categoriaDtoResponse);

        PagamentoDespesaDtoResponse pagamentoDespesaDtoResponse = new PagamentoDespesaDtoResponse(
                pagamento.getId(),
                pagamento.getDiaPagamento(),
                despesaDtoResponse
        );

        return pagamentoDespesaDtoResponse;
    }
}
