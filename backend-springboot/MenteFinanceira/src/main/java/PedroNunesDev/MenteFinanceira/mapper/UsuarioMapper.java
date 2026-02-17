package PedroNunesDev.MenteFinanceira.mapper;

import PedroNunesDev.MenteFinanceira.dto.request.UsuarioDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTOResponse toDTO(Usuario usuario);

    Usuario toModel(UsuarioDTORequest usuarioDTORequest);
}
