package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.TokenVerificacaoRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenVerificacaoService {

    @Autowired
    private TokenVerificacaoRepository tokenVerificacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public TokenVerificacao gerarTokenDeVerificacao(Usuario usuario){

        TokenVerificacao tokenVerificacaoExistente = tokenVerificacaoRepository.findByEmail(usuario.getEmail())
                .orElse(null);

        if (tokenVerificacaoExistente == null) {
            String token = UUID.randomUUID().toString();

            TokenVerificacao tokenVerificacao = new TokenVerificacao(
                    token,
                    false,
                    LocalDateTime.now().plusMinutes(30),
                    usuario.getNome(),
                    usuario.getEmail()
            );

            return tokenVerificacaoRepository.save(tokenVerificacao);
        }
        else {
            TokenVerificacao tokenVerificacao = validarTokenDeVerificacao(tokenVerificacaoExistente.getToken());
        }

    }

    public TokenVerificacao validarTokenDeVerificacao(String token){

        TokenVerificacao tokenVerificacao = tokenVerificacaoRepository.findByToken(token).orElse(null);

        if (token == null){
            return null;
        }
        else if(tokenVerificacao.isAtivo()){
            return null;
        }
        else if(LocalDateTime.now().isAfter(tokenVerificacao.getDuracao())){
            return null;
        }

        tokenVerificacao.setAtivo(true);
        return tokenVerificacaoRepository.save(tokenVerificacao);
    }
}
