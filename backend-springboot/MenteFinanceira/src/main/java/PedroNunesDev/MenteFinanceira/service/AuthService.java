package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.exception.UsuarioNaoVerificadoException;
import PedroNunesDev.MenteFinanceira.mapper.UsuarioMapper;
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
    @Autowired
    private UsuarioMapper usuarioMapper;

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

    public String validarLogin(LoginDTO loginDTO){

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        //Valida se o email ja foi verificado
        if (!usuario.isVerificacaoEmail()) throw new UsuarioNaoVerificadoException("Usuário não verificado");

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        return tokenService.genereteToken((Usuario) authentication.getPrincipal());
    }

    public Usuario me(){

        Usuario usuarioAuth = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return usuarioRepository.findById(usuarioAuth.getIdUsuario()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
