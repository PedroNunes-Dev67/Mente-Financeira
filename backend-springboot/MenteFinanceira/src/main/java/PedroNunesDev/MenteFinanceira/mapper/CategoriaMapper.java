package PedroNunesDev.MenteFinanceira.mapper;

import PedroNunesDev.MenteFinanceira.dto.request.CategoriaDTO;
import PedroNunesDev.MenteFinanceira.dto.response.CategoriaDtoResponse;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    Categoria toModel(CategoriaDTO categoriaDTO);

    CategoriaDtoResponse toDTO(Categoria categoria);
}
