package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
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
    public ResponseEntity<TokenVerificacaoDTO> login(@RequestBody @Valid LoginDTO loginDTO){

        TokenVerificacaoDTO token = usuarioService.login(loginDTO);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<TokenVerificacao> cadastrarUsuario(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest){

        TokenVerificacao tokenVerificacao = usuarioService.cadastrarUsuario(usuarioDTORequest);

        return ResponseEntity.ok(tokenVerificacao);
    }
}
