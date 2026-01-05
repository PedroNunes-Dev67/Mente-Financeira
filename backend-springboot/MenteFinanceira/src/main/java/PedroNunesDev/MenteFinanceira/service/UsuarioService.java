package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
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
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenVerificacaoService tokenVerificacaoService;
    @Autowired
    private BCryptPasswordEncoder bcrypt;

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

            TokenVerificacao tokenVerificacao = gerarToken(usuario);

            return tokenVerificacao;
        }
        else{
            TokenVerificacao tokenVerificacaoExistente = tokenVerificacaoService.analisarTokenVerificacaoUsuario(usuario);

            if (tokenVerificacaoExistente == null){

                return gerarToken(usuario);

            }

            return tokenVerificacaoExistente;
        }
    }

    public TokenVerificacao gerarToken(Usuario usuario){

        return tokenVerificacaoService.gerarTokenDeVerificacao(usuario);
    }

    public UsuarioDTOResponse validarTokenVerificao(TokenVerificacaoDTO tokenVerificacaoDTO){

        TokenVerificacao tokenVerificacao = tokenVerificacaoService.validarTokenDeVerificacao(tokenVerificacaoDTO);

        if (tokenVerificacao == null) throw new RuntimeException();

        Usuario usuario = usuarioRepository.findById(tokenVerificacao.getIdUsuario())
                .orElseThrow(() -> new RuntimeException());

        usuario.setVerificacaoEmail(true);

        usuario = usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public String login(LoginDTO loginDTO){

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new RuntimeException("usuário não encontrado"));


        if (!usuario.isVerificacaoEmail()) throw new RuntimeException();

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

        Usuario usuario = usuarioRepository.findById(usuarioAuth.getId()).orElseThrow(() -> new RuntimeException());

        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNome()
        );
    }
}
