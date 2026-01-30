package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.exception.RecursoInvalidoException;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.TokenVerificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenVerificacaoService {

    @Autowired
    private TokenVerificacaoRepository tokenVerificacaoRepository;

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
    public TokenVerificacao validarTokenDeVerificacao(TokenVerificacaoDTORequest tokenVerificacaoDTORequest){

        Optional<TokenVerificacao> tokenVerificacaoBuscado = tokenVerificacaoRepository.findByToken(tokenVerificacaoDTORequest.token());

        if (tokenVerificacaoBuscado.isEmpty()) throw new ResourceNotFoundException("Token de verificação não encontrado");

        TokenVerificacao tokenVerificacaoExistente = tokenVerificacaoBuscado.get();

        if (tokenVerificacaoExistente.isAtivo()){
            throw new RecursoInvalidoException("Token de verificação já está ativo");
        }
        else if (LocalDateTime.now().isAfter(tokenVerificacaoExistente.getDuracao())){
            throw new RecursoInvalidoException("Token de verificação está expirado");
        }

        confirmarToken(tokenVerificacaoExistente);

        return tokenVerificacaoExistente;
    }

    @Transactional
    private void confirmarToken(TokenVerificacao tokenVerificacao){

        tokenVerificacao.setAtivo(true);
        tokenVerificacaoRepository.save(tokenVerificacao);
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
