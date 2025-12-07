package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.*;
import PedroNunesDev.MenteFinanceira.service.UsuarioService;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> findById(@PathVariable Long id){
        UsuarioDTOResponse usuario = usuarioService.findById(id);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTOResponse>> findAll(){

        List<UsuarioDTOResponse> list = usuarioService.findAll();
        return ResponseEntity.ok(list);
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

        return ResponseEntity.created(createdUri(usuario.id())).body(usuario);
    }

    private URI createdUri(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> updateUsuario(@RequestBody @Valid SenhaDTO senhaDTO,
                                                            @PathVariable Long id){
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.updateUsuario(senhaDTO, id);

        return ResponseEntity.ok(usuarioDTOResponse);
    }
}
