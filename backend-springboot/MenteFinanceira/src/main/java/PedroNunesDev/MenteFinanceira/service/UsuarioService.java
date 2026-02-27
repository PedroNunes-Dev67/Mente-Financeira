package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.request.UsuarioDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.TokenVerificacaoDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.mapper.TokenVerificacaoMapper;
import PedroNunesDev.MenteFinanceira.mapper.UsuarioMapper;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.UsuarioRole;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private TokenVerificacaoService tokenVerificacaoService;
    private BCryptPasswordEncoder bcrypt;
    private AuthService authService;
    private UsuarioMapper usuarioMapper;
    private TokenVerificacaoMapper tokenVerificacaoMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, TokenVerificacaoService tokenVerificacaoService, BCryptPasswordEncoder bcrypt, AuthService authService, UsuarioMapper usuarioMapper, TokenVerificacaoMapper tokenVerificacaoMapper) {
        this.usuarioRepository = usuarioRepository;
        this.tokenVerificacaoService = tokenVerificacaoService;
        this.bcrypt = bcrypt;
        this.authService = authService;
        this.usuarioMapper = usuarioMapper;
        this.tokenVerificacaoMapper = tokenVerificacaoMapper;
    }

    @Transactional
    public TokenVerificacaoDtoResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest){

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(usuarioDTORequest.email()).orElse(null);

        if (usuario == null){

            String senha = bcrypt.encode(usuarioDTORequest.senha());

            usuario = new Usuario(
                    usuarioDTORequest.nome(),
                    usuarioDTORequest.email(),
                    senha,
                    UsuarioRole.USUARIO
            );

            usuario = usuarioRepository.save(usuario);

            TokenVerificacao tokenVerificacao = tokenVerificacaoService.gerarTokenDeVerificacao(usuario);

            return tokenVerificacaoMapper.toDTO(tokenVerificacao);
        }
        else{

            if (usuario.isVerificacaoEmail()) throw new RuntimeException();

            //Pega algum possivel token que ainda está para uso
            TokenVerificacao tokenVerificacaoExistente = tokenVerificacaoService.analisarTokenVerificacaoUsuario(usuario);

            if (tokenVerificacaoExistente == null){

                TokenVerificacao tokenVerificacao = tokenVerificacaoService.gerarTokenDeVerificacao(usuario);

                return tokenVerificacaoMapper.toDTO(tokenVerificacao);
            }

            return tokenVerificacaoMapper.toDTO(tokenVerificacaoExistente);
        }
    }

    @Transactional
    public TokenVerificacaoDtoResponse login(LoginDTO loginDTO){

        String token = authService.validarLogin(loginDTO);

        return new TokenVerificacaoDtoResponse(token);
    }

    @Transactional
    public UsuarioDTOResponse me(){

        Usuario usuario = authService.me();
        return usuarioMapper.toDTO(usuario);
    }
}
