package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.TokenVerificacaoDTO;
import PedroNunesDev.MenteFinanceira.dto.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;

import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import PedroNunesDev.MenteFinanceira.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private TokenVerificacaoService tokenVerificacaoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public UsuarioDTOResponse confirmarValidacaoDeEmail(TokenVerificacaoDTO tokenVerificacaoDTO){

        TokenVerificacao tokenVerificacao = tokenVerificacaoService.validarTokenDeVerificacao(tokenVerificacaoDTO);

        if (tokenVerificacao == null) throw new RuntimeException();

        Usuario usuario = usuarioRepository.findById(tokenVerificacao.getIdUsuario()).orElseThrow(() -> new RuntimeException());

        usuario.setVerificacaoEmail(true);
        usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public String validarLogin(LoginDTO loginDTO){

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new RuntimeException("usuário não encontrado"));


        if (!usuario.isVerificacaoEmail()) throw new RuntimeException();

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        return tokenService.genereteToken((Usuario) authentication.getPrincipal());
    }

    public UsuarioDTOResponse me(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNome()
        );
    }
}
