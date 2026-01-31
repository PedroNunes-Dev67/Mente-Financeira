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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AuthService authService;

    @Transactional
    public DespesaDtoResponse cadastrarDespesa(DespesaDTORequest financaDTORequest){

        Usuario usuario = authService.me();

        Categoria categoria = categoriaRepository.findById(financaDTORequest.id_categoria()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Despesa despesa = new Despesa(
                financaDTORequest.titulo(),
                financaDTORequest.valor(),
                financaDTORequest.diaDePagamento(),
                TipoDespesa.valueOf(financaDTORequest.tipoDespesa()),
                DespesaStatus.PENDENTE,
                usuario,
                categoria);

        despesaRepository.save(despesa);

        return new DespesaDtoResponse(
                despesa.getIdDespesa(),
                despesa.getTitulo(),
                despesa.getValor(),
                despesa.getTipoDespesa(),
                despesa.getDespesaStatus(),
                new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail()),
                new CategoriaDtoResponse(categoria.getIdCategoria(), categoria.getNome()),
                despesa.getPagamentos()
        );
    }

    @Transactional
    public List<Despesa> buscarDespesasPorUsuario(){

        Usuario usuario = authService.me();

        return despesaRepository.findByUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasPorCategoria(Long id){

        Usuario usuarioAuth = authService.me();

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        return despesaRepository.findByUsuarioAndCategoria(usuarioAuth,categoria);
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasPendentes(){

        Usuario usuario = authService.me();

        return despesaRepository.findByUsuarioAndDespesaStatus(usuario, DespesaStatus.PENDENTE);
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasPagas(){

        Usuario usuario = authService.me();

        return despesaRepository.findByUsuarioAndDespesaStatus(usuario, DespesaStatus.PAGO);
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasRecorrentesUsuario(){

        Usuario usuario = authService.me();

        return despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.RECORRENTE, usuario);
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasNaoRecorrentesUsuario(){

        Usuario usuario = authService.me();

        return despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.NAO_RECORRENTE, usuario);
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasNaoRecorrentesUsuarioPagas(int pagina, int items){

        Usuario usuario = authService.me();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.NAO_RECORRENTE, usuario, DespesaStatus.PAGO, PageRequest.of(pagina,items)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasNaoRecorrentesUsuarioPendentes(int pagina, int items){

        Usuario usuario = authService.me();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.NAO_RECORRENTE, usuario, DespesaStatus.PENDENTE, PageRequest.of(pagina,items)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasRecorrentesUsuarioPagas(int pagina, int items){

        Usuario usuario = authService.me();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.RECORRENTE, usuario,DespesaStatus.PAGO, PageRequest.of(pagina,items)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Despesa> despesasRecorrentesUsuarioPendentes(int pagina, int items){

        Usuario usuario = authService.me();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.RECORRENTE, usuario,DespesaStatus.PENDENTE, PageRequest.of(pagina,items)).getContent();
    }
}
