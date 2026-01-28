package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.request.UsuarioDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.TokenVerificacaoDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
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

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenVerificacaoService tokenVerificacaoService;
    @Autowired
    private BCryptPasswordEncoder bcrypt;
    @Autowired
    private AuthService authService;

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

            return new TokenVerificacaoDtoResponse(tokenVerificacao.getToken());
        }
        else{
            //Pega algum possivel token que ainda está para uso
            TokenVerificacao tokenVerificacaoExistente = tokenVerificacaoService.analisarTokenVerificacaoUsuario(usuario);

            if (tokenVerificacaoExistente == null){

                TokenVerificacao tokenVerificacao = tokenVerificacaoService.gerarTokenDeVerificacao(usuario);

                return new TokenVerificacaoDtoResponse(tokenVerificacao.getToken());
            }

            return new TokenVerificacaoDtoResponse(tokenVerificacaoExistente.getToken());
        }
    }

    public TokenVerificacaoDTORequest login(LoginDTO loginDTO){

        String token = authService.validarLogin(loginDTO);

        return new TokenVerificacaoDTORequest(token);
    }

    public UsuarioDTOResponse me(){

        Usuario usuario = authService.me();
        return new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

}
