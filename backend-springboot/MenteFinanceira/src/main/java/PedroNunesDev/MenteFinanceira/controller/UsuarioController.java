package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.request.TokenVerificacaoDTORequest;
import PedroNunesDev.MenteFinanceira.dto.request.UsuarioDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.TokenVerificacaoDtoResponse;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuario Controller", description = "Controlador de todas as funções relacionadas ao usuário")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTOResponse> me(){

        UsuarioDTOResponse usuarioDTOResponse = usuarioService.me();

        return ResponseEntity.ok(usuarioDTOResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenVerificacaoDtoResponse> login(@RequestBody @Valid LoginDTO loginDTO){

        TokenVerificacaoDtoResponse token = usuarioService.login(loginDTO);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<TokenVerificacaoDtoResponse> cadastrarUsuario(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest){

        TokenVerificacaoDtoResponse tokenVerificacao = usuarioService.cadastrarUsuario(usuarioDTORequest);

        return ResponseEntity.ok(tokenVerificacao);
    }
}
