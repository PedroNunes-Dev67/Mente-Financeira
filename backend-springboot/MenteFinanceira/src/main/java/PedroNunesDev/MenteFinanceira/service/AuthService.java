package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;

import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import PedroNunesDev.MenteFinanceira.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UsuarioDTOResponse confirmarValidacaoDeEmail(TokenVerificacaoDTORequest tokenVerificacaoDTORequest){

        //Passa pela validação de token para ver os criterios
        TokenVerificacao tokenVerificacao = tokenVerificacaoService.validarTokenDeVerificacao(tokenVerificacaoDTORequest);

        if (tokenVerificacao == null) throw new RuntimeException();

        Usuario usuario = usuarioRepository.findById(tokenVerificacao.getIdUsuario()).orElseThrow(() -> new RuntimeException());

        usuario.setVerificacaoEmail(true);
        usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    @Transactional
    public String validarLogin(LoginDTO loginDTO){

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new RuntimeException("usuário não encontrado"));

        //Valida se o email ja foi verificado
        if (!usuario.isVerificacaoEmail()) throw new RuntimeException();

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        return tokenService.genereteToken((Usuario) authentication.getPrincipal());
    }

    public Usuario me(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());
    }
}
