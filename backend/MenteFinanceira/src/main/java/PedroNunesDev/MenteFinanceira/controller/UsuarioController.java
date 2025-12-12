package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.service.UsuarioService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<UsuarioDTOResponse> cadastrarUsuario(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest){

        UsuarioDTOResponse usuario = usuarioService.cadastrarUsuario(usuarioDTORequest);

        if (usuario == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> updateUsuario(@PathVariable Long id, @RequestBody @Valid SenhaDTO senhaDTO){

        UsuarioDTOResponse usuarioDTOResponse = usuarioService.updateUsuario(id, senhaDTO);

        return ResponseEntity.ok(usuarioDTOResponse);
    }
}
