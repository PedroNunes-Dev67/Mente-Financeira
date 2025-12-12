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
                usuario,
                categoria
        ));
    }

    public List<Financa> buscandoFinancas(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return usuario.getList();
    }
}
