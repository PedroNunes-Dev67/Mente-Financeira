package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.RefreshDtoRequest;
import PedroNunesDev.MenteFinanceira.exception.RecursoInvalidoException;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.security.RefreshToken;
import PedroNunesDev.MenteFinanceira.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken criarRefreshToken(Usuario usuario){

        //Deleta o token de refresh se existir
        refreshTokenRepository.deleteByUsuario(usuario);

        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), usuario, Instant.now().plusSeconds(2592000));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validarRefresh(String token){

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token de refresh não encontrado"));

        if (refreshToken.isExpire()){
            refreshTokenRepository.delete(refreshToken);
            throw new RecursoInvalidoException("Token de refresh inválido");
        }

        return refreshToken;
    }

    public void logout(RefreshDtoRequest refreshDtoRequest){

        RefreshToken refreshToken = validarRefresh(refreshDtoRequest.token());

        refreshTokenRepository.delete(refreshToken);
    }
}
