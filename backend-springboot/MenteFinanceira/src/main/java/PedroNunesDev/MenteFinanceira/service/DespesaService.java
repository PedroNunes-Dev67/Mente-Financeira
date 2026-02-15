package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.CategoriaDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.DespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PagamentoService pagamentoService;

    @Transactional
    public DespesaDtoResponse cadastrarDespesa(DespesaDTORequest despesaDTORequest){

        Usuario usuario = authService.me();

        Categoria categoria = categoriaRepository.findById(despesaDTORequest.idCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Despesa despesa = new Despesa(
                despesaDTORequest.titulo(),
                despesaDTORequest.valor(),
                TipoDespesa.from(despesaDTORequest.tipoDespesa()),
                usuario,
                despesaDTORequest.dataVencimento(),
                despesaDTORequest.parcelasTotais(),
                despesaDTORequest.parcelasPagas(),
                categoria);

        Despesa despesaSalva = despesaRepository.save(despesa);

        pagamentoService.verificarDespesaNaoRecorrente(despesaSalva, despesaDTORequest.tipoPagamento());

        return new DespesaDtoResponse(
                despesaSalva,
                new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail()),
                new CategoriaDtoResponse(categoria)
                );
    }

    @Transactional
    public void deletarDespesa(Long idDespesa){

        Usuario usuario = authService.me();

        Optional<Despesa> despesa = despesaRepository.findByIdDespesaAndUsuario(idDespesa,usuario);

        if (despesa.isEmpty()) throw new ResourceNotFoundException("Despesa não encontrada");
        else{
            despesaRepository.delete(despesa.get());
        }
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> buscarDespesasPorUsuario(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByUsuario(usuario, criarPageable(pagina,items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorCategoria(Long id, int pagina,int items){

        Usuario usuario = authService.me();

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Page<Despesa> paginacaoCriada = despesaRepository.findByUsuarioAndCategoria(usuario,categoria, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPendentes(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByUsuarioAndDespesaStatus(usuario, DespesaStatus.PENDENTE, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPagas(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByUsuarioAndDespesaStatus(usuario, DespesaStatus.PAGO, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasRecorrentesUsuario(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.RECORRENTE, usuario, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasNaoRecorrentesUsuario(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.NAO_RECORRENTE, usuario, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasNaoRecorrentesUsuarioPagas(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.NAO_RECORRENTE, usuario, DespesaStatus.PAGO, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasNaoRecorrentesUsuarioPendentes(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.NAO_RECORRENTE, usuario, DespesaStatus.PENDENTE, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasRecorrentesUsuarioPagas(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.RECORRENTE, usuario,DespesaStatus.PAGO, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasRecorrentesUsuarioPendentes(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.RECORRENTE, usuario,DespesaStatus.PENDENTE, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
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

    private Page<DespesaDtoResponse> converterParaDTO(Page<Despesa> paginacaoCriada){

        return paginacaoCriada
                .map(despesa -> {

                    return new DespesaDtoResponse(
                            despesa,
                            new UsuarioDTOResponse(despesa.getUsuario().getId(), despesa.getUsuario().getNome(),despesa.getUsuario().getEmail()),
                            new CategoriaDtoResponse(despesa.getCategoria())
                    );
                });
    }

    private PageRequest criarPageable(int pagina, int items){

        //Faz normalização da pagina e items por página
        return PageRequest.of(normalizaPagina(pagina), normalizaTamanhoDePagina(items));
    }
}
