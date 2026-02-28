package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Usuario Controller",description = "Controlador de todas as fuções relacionadas a autenticação do usuário")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@ApiResponse(responseCode = "403",description = "Erro de autenticação do usuário")
@RestController
@RequestMapping("/auth")
public class AuthUsuarioController {

    private AuthService authService;

    public AuthUsuarioController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Valida token de verificação de email",
            description = "Token passado passa por validações para verificar usuário" +
                    " `ABERTO`")
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> validarTokenValidacaoEmail(@RequestBody @Valid TokenVerificacaoDTORequest tokenVerificacaoDTORequest){

        UsuarioDTOResponse usuarioDTOResponse = authService.confirmarValidacaoDeEmail(tokenVerificacaoDTORequest);

        return ResponseEntity.ok(usuarioDTOResponse);
    }
}
