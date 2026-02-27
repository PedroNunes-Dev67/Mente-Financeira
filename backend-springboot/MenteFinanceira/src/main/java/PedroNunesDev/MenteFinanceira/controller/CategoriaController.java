package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.CategoriaDTO;
import PedroNunesDev.MenteFinanceira.dto.response.CategoriaDtoResponse;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Categoria Controller", description = "Controlador de todas as funções relacionadas as categorias")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Pesquisa categoria por id",
            description = "Id passado é buscado no banco de dados, trazendo uma categoria caso exista" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDtoResponse> findById(@PathVariable Long id){

        CategoriaDtoResponse categoria = categoriaService.findById(id);

        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Lista todas as categorias",
            description = "Lista de todos as categorias buscada no banco" +
                    " `AUTENTICAÇÃO NESCESSÁRIA`")
    @GetMapping
    public ResponseEntity<List<CategoriaDtoResponse>> findAll(){

        List<CategoriaDtoResponse> categorias = categoriaService.findAll();

        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Criar nova categoria",
            description = "Os atributos passados passam por uma validação e caso passe, cria uma nova categoria " +
                    " **Roles nescessárias:** `ADMIN`")
    @PostMapping
    public ResponseEntity<CategoriaDtoResponse> createCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){

        CategoriaDtoResponse categoria = categoriaService.createCategoria(categoriaDTO);

        return ResponseEntity.created(generateURI(categoria.getId())).body(categoria);
    }

    private URI generateURI(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();
    }

    @Operation(summary = "Atualizar categoria",
            description = "Passa um id para encontrar categoria, e um body para atualizar a categoria " +
                    " **Roles nescessárias:** `ADMIN`")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDtoResponse> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){

        CategoriaDtoResponse categoria = categoriaService.updateCategoria(id,categoriaDTO);

        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Deletar categoria",
            description = "Passa um id para encontrar categoria e deletar" +
                    " **Roles nescessárias:** `ADMIN`")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id){

        categoriaService.deleteCategoria(id);

        return ResponseEntity.ok().build();
    }
}
