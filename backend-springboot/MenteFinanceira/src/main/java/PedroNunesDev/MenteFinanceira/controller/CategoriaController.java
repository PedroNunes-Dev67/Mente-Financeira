package PedroNunesDev.MenteFinanceira.controller;

import PedroNunesDev.MenteFinanceira.dto.request.CategoriaDTO;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.security.SecurityConfiguration;
import PedroNunesDev.MenteFinanceira.service.CategoriaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id){

        Categoria categoria = categoriaService.findById(id);

        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll(){

        List<Categoria> categorias = categoriaService.findAll();

        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){

        Categoria categoria = categoriaService.createCategoria(categoriaDTO);

        return ResponseEntity.created(generateURI(categoria.getIdCategoria())).body(categoria);
    }

    private URI generateURI(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){

        Categoria categoria = categoriaService.updateCategoria(id,categoriaDTO);

        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id){

        categoriaService.deleteCategoria(id);

        return ResponseEntity.ok().build();
    }
}
