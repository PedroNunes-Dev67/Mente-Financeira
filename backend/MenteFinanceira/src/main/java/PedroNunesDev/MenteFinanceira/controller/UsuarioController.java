package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import PedroNunesDev.MenteFinanceira.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TokenDTOResponse> login(@RequestBody @Valid LoginDTO loginDTO){

        String token = usuarioService.login(loginDTO);

        return ResponseEntity.ok(new TokenDTOResponse(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<TokenVerificacao> cadastrarUsuario(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest){

        TokenVerificacao tokenVerificacao = usuarioService.cadastrarUsuario(usuarioDTORequest);

        return ResponseEntity.ok(tokenVerificacao);

    }

    @PutMapping("/cadastro/auth")
    public ResponseEntity<UsuarioDTOResponse> validarTokenVerificacao(@RequestParam String token){

        UsuarioDTOResponse usuario = usuarioService.validarTokenVerificao(token);

        return ResponseEntity.ok(usuario);
    }
}
