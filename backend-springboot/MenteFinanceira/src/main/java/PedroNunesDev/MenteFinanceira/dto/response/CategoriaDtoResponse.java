package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CategoriaDtoResponse{

    @Schema(example = "6")
    private Long id;
    @Schema(example = "Educação")
    private String nome;

    public CategoriaDtoResponse(Categoria categoria) {

        this.id = categoria.getIdCategoria();
        this.nome = categoria.getNome();
    }
}
