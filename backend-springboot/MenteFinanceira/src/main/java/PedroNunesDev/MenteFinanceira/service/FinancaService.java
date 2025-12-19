package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.FinancaDTORequest;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Financa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.FinancaStatus;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.FinancaRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancaService {

    @Autowired
    private FinancaRepository financaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Financa cadastrarFinanca(FinancaDTORequest financaDTORequest){

        Usuario usuario = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Categoria categoria = categoriaRepository.findById(financaDTORequest.id_categoria()).orElseThrow(() -> new RuntimeException());


        return financaRepository.save(new Financa(
                financaDTORequest.titulo(),
                financaDTORequest.valor(),
                FinancaStatus.PENDENTE,
                financaDTORequest.diaDePagamento(),
                usuario,
                categoria
        ));
    }

    public List<Financa> buscarFinancasPorUsuario(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return usuario.getList();
    }

    public Financa pagamentoFinanca(Long id){

        Financa financa = financaRepository.findById(id).orElseThrow(() -> new RuntimeException());

        financa.setStatus(FinancaStatus.PAGO);
        return financaRepository.save(financa);
    }

    public List<Financa> financaPorCategoria(Long id){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException());

        List<Financa> listaDefinancas = usuario.getList();

        return listaDefinancas.stream()
                .filter(financa -> financa.getCategoria() == categoria).toList();
    }

    public List<Financa> financasPendentes(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return usuario.getList()
                .stream().filter(financa -> financa.getStatus() == FinancaStatus.PENDENTE).toList();

    }

    public List<Financa> financasPagas(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return usuario.getList()
                .stream().filter(financa -> financa.getStatus() == FinancaStatus.PAGO).toList();

    }
}
