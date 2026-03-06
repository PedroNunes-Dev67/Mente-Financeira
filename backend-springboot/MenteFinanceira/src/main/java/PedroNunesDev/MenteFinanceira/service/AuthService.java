package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTORequest;
import PedroNunesDev.MenteFinanceira.dto.request.RefreshDtoRequest;
import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.LoginDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.exception.UsuarioNaoVerificadoException;
import PedroNunesDev.MenteFinanceira.mapper.UsuarioMapper;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.security.RefreshToken;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import PedroNunesDev.MenteFinanceira.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private TokenVerificacaoService tokenVerificacaoService;
    private UsuarioRepository usuarioRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UsuarioMapper usuarioMapper;
    private RefreshTokenService refreshTokenService;

    public AuthService(TokenVerificacaoService tokenVerificacaoService, UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, TokenService tokenService, UsuarioMapper usuarioMapper, RefreshTokenService refreshTokenService) {
        this.tokenVerificacaoService = tokenVerificacaoService;
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioMapper = usuarioMapper;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public UsuarioDTOResponse confirmarValidacaoDeEmail(TokenVerificacaoDTORequest tokenVerificacaoDTORequest){

        //Passa pela validação de token para ver os criterios
        TokenVerificacao tokenVerificacao = tokenVerificacaoService.validarTokenDeVerificacao(tokenVerificacaoDTORequest);

        //Busca usuário pelo ID contido no Token
        Usuario usuario = usuarioRepository.findById(tokenVerificacao.getIdUsuario()).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        //Atualiza para usuário verificado
        atualizarVerificacaoEmailUsuario(usuario);

        return usuarioMapper.toDTO(usuario);
    }

    private void atualizarVerificacaoEmailUsuario(Usuario usuario){

        usuario.setVerificacaoEmail(true);
        usuarioRepository.save(usuario);
    }

    public LoginDtoResponse validarLogin(LoginDTORequest loginDTORequest){

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(loginDTORequest.email()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        //Valida se o email ja foi verificado
        if (!usuario.isVerificacaoEmail()) throw new UsuarioNaoVerificadoException("Usuário não verificado");

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTORequest.email(), loginDTORequest.senha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        String acessToken = tokenService.genereteToken((Usuario) authentication.getPrincipal());
        RefreshToken refreshToken = refreshTokenService.criarRefreshToken((Usuario) authentication.getPrincipal());

        LoginDtoResponse loginDtoResponse = new LoginDtoResponse(acessToken,refreshToken.getToken());

        return loginDtoResponse;
    }

    public LoginDtoResponse refreshToken(RefreshDtoRequest refreshDtoRequest){

        RefreshToken refreshToken = refreshTokenService.validarRefresh(refreshDtoRequest.token());

        String acessToken = tokenService.genereteToken(refreshToken.getUsuario());

        LoginDtoResponse loginDtoResponse = new LoginDtoResponse(acessToken,refreshToken.getToken());

        return loginDtoResponse;
    }

    public void logout(RefreshDtoRequest refreshDtoRequest){

        refreshTokenService.logout(refreshDtoRequest);
    }

    public Usuario me(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return usuarioRepository.findById(usuarioAuth.getIdUsuario()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
