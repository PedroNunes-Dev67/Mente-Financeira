package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.UsuarioRole;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import PedroNunesDev.MenteFinanceira.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest){

        if (repository.findByEmail(usuarioDTORequest.email()) != null) return null;

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTORequest.senha());
        Usuario usuario = new Usuario(usuarioDTORequest.nome(), usuarioDTORequest.email(), senhaCriptografada, UsuarioRole.USUARIO);

        repository.save(usuario);
        return new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public String login(LoginDTO loginDTO){

        Usuario usuario = (Usuario) repository.findByEmail(loginDTO.email());

        if (usuario == null) throw new RuntimeException();
        else if (!usuario.isVerificacaoEmail()) throw new RuntimeException();

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.genereteToken((Usuario) authentication.getPrincipal());

        return token;
    }


    public UsuarioDTOResponse me(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = repository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNome()
        );
    }
}
