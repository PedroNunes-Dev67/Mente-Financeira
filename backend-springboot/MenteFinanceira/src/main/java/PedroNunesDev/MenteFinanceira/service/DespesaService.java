package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Pagamento_Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import PedroNunesDev.MenteFinanceira.repository.PagamentoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Despesa cadastrarDespesa(DespesaDTORequest financaDTORequest){

        Usuario usuario = getUsuarioContext();

        Categoria categoria = categoriaRepository.findById(financaDTORequest.id_categoria()).orElseThrow(() -> new RuntimeException());


        return despesaRepository.save(new Despesa(
                financaDTORequest.titulo(),
                financaDTORequest.valor(),
                financaDTORequest.diaDePagamento(),
                TipoDespesa.valueOf(financaDTORequest.tipoDespesa()),
                DespesaStatus.PENDENTE,
                usuario,
                categoria
        ));
    }

    public List<Despesa> buscarDespesasPorUsuario(){

        Usuario usuario = getUsuarioContext();

        return usuario.getDespesas();
    }

    public List<Despesa> despesasPorCategoria(Long id){

        Usuario usuarioAuth = getUsuarioContext();

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException());

        return despesaRepository.findByUsuarioAndCategoria(usuarioAuth,categoria);
    }

    public List<Despesa> despesasPendentes(){

        Usuario usuario = getUsuarioContext();

        return usuario.getDespesas()
                .stream()
                .filter(des -> des.getDespesaStatus() == DespesaStatus.PENDENTE)
                .toList();
    }

    public List<Despesa> despesasPagas(){

        Usuario usuario = getUsuarioContext();

        return usuario.getDespesas()
                .stream()
                .filter(des -> des.getDespesaStatus() == DespesaStatus.PAGO)
                .toList();

    }

    public List<Despesa> despesasRecorrentesUsuario(){

        Usuario usuario = getUsuarioContext();

        return despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.RECORRENTE, usuario);
    }

    public List<Despesa> despesasNaoRecorrentesUsuario(){

        Usuario usuario = getUsuarioContext();

        return despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.NAO_RECORRENTE, usuario);
    }

    public List<Despesa> despesasNaoRecorrentesUsuarioPagas(){

        Usuario usuario = getUsuarioContext();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.NAO_RECORRENTE, usuario, DespesaStatus.PAGO);
    }

    public List<Despesa> despesasNaoRecorrentesUsuarioPendentes(){

        Usuario usuario = getUsuarioContext();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.NAO_RECORRENTE, usuario, DespesaStatus.PENDENTE);
    }

    public List<Despesa> despesasRecorrentesUsuarioPagas(){

        Usuario usuario = getUsuarioContext();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.RECORRENTE, usuario,DespesaStatus.PAGO);
    }

    public List<Despesa> despesasRecorrentesUsuarioPendentes(){

        Usuario usuario = getUsuarioContext();

        return despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.RECORRENTE, usuario,DespesaStatus.PENDENTE);
    }
    private Usuario getUsuarioContext(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());
    }
}
