package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.request.UsuarioDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.TokenVerificacaoDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuario Controller", description = "Controlador de todas as funções relacionadas ao usuário")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Retorna Usuario autenticado",
            description = "Busca usuario que está autenticado pelo JWT" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTOResponse> me(){

        UsuarioDTOResponse usuarioDTOResponse = usuarioService.me();

        return ResponseEntity.ok(usuarioDTOResponse);
    }

    @Operation(summary = "Login do usuário",
            description = "Realiza o login do usuário" +
                    " `ABERTO`")
    @PostMapping("/login")
    public ResponseEntity<TokenVerificacaoDtoResponse> login(@RequestBody @Valid LoginDTO loginDTO){

        TokenVerificacaoDtoResponse token = usuarioService.login(loginDTO);

        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Cadastrar Usuário",
            description = "Realiza o cadastro de usuário" +
                    " `ABERTO`")
    @PostMapping("/cadastro")
    public ResponseEntity<TokenVerificacaoDtoResponse> cadastrarUsuario(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest){

        TokenVerificacaoDtoResponse tokenVerificacao = usuarioService.cadastrarUsuario(usuarioDTORequest);

        return ResponseEntity.ok(tokenVerificacao);
    }
}
