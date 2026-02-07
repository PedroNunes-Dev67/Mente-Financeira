package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import lombok.Getter;

@Getter
public class CategoriaDtoResponse{

    private Long id;
    private String nome;

    public CategoriaDtoResponse(Categoria categoria) {

        this.id = categoria.getIdCategoria();
        this.nome = categoria.getNome();
    }
}
