package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTO;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.TokenVerificacaoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TokenVerificacaoService {

    @Autowired
    private TokenVerificacaoRepository tokenVerificacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public TokenVerificacao gerarTokenDeVerificacao(Usuario usuario){

            String token = UUID.randomUUID().toString();

            TokenVerificacao tokenVerificacao = new TokenVerificacao(
                    token,
                    false,
                    LocalDateTime.now().plusMinutes(30),
                    usuario.getId()
            );

            return tokenVerificacaoRepository.save(tokenVerificacao);
    }

    @Transactional
    public TokenVerificacao validarTokenDeVerificacao(TokenVerificacaoDTO tokenVerificacaoDTO){

        TokenVerificacao tokenVerificacao = tokenVerificacaoRepository.findByToken(tokenVerificacaoDTO.token()).orElse(null);

        if (tokenVerificacao == null){
            return null;
        }
        else if(tokenVerificacao.isAtivo()){
            return null;
        }
        else if(LocalDateTime.now().isAfter(tokenVerificacao.getDuracao())){
            return null;
        }

        confirmarToken(tokenVerificacao);

        return tokenVerificacao;
    }

    @Transactional
    private TokenVerificacao confirmarToken(TokenVerificacao tokenVerificacao){

        tokenVerificacao.setAtivo(true);
        return tokenVerificacaoRepository.save(tokenVerificacao);
    }

    @Transactional(readOnly = true)
    public TokenVerificacao analisarTokenVerificacaoUsuario(Usuario usuario){

        List<TokenVerificacao> tokenVerificacaoExistentes = tokenVerificacaoRepository.findByIdUsuario(usuario.getId());

        if (tokenVerificacaoExistentes.isEmpty()){
            return null;
        }
        else{
            return tokenVerificacaoExistentes
                    .stream()
                    .filter(token -> !token.isAtivo() && !LocalDateTime.now().isAfter(token.getDuracao()))
                    .findFirst().orElse(null);
        }
    }
}
