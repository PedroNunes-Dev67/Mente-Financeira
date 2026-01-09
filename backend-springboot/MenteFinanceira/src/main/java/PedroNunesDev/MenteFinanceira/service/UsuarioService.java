package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.UsuarioRole;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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

    public TokenVerificacao cadastrarUsuario(UsuarioDTORequest usuarioDTORequest){

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

            return tokenVerificacao;
        }
        else{
            TokenVerificacao tokenVerificacaoExistente = tokenVerificacaoService.analisarTokenVerificacaoUsuario(usuario);

            if (tokenVerificacaoExistente == null){

                return tokenVerificacaoService.gerarTokenDeVerificacao(usuario);

            }

            return tokenVerificacaoExistente;
        }
    }

    public TokenVerificacaoDTO login(LoginDTO loginDTO){

        String token = authService.validarLogin(loginDTO);

        return new TokenVerificacaoDTO(token);
    }

    public UsuarioDTOResponse me(){

        return authService.me();
    }

}
