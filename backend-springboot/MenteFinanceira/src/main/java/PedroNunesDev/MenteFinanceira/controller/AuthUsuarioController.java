package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.TokenVerificacaoDTO;
import PedroNunesDev.MenteFinanceira.dto.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUsuarioController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> validarTokenValidacaoEmail(@RequestBody @Valid TokenVerificacaoDTO tokenVerificacaoDTO){

        UsuarioDTOResponse usuarioDTOResponse = authService.confirmarValidacaoDeEmail(tokenVerificacaoDTO);

        return ResponseEntity.ok(usuarioDTOResponse);
    }
}
