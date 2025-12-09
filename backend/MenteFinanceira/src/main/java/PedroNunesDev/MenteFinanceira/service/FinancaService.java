package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.FinancaDTORequest;
import PedroNunesDev.MenteFinanceira.model.Financa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.FinancaRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FinancaService {

    @Autowired
    private FinancaRepository financaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Financa cadastrarFinanca(FinancaDTORequest financaDTORequest){

        Usuario usuario = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return new Financa(
                financaDTORequest.titulo(),
                financaDTORequest.valor(),
                financaDTORequest.status(),
                usuario,
                financaDTORequest.vencinmento()
        );
    }
}
