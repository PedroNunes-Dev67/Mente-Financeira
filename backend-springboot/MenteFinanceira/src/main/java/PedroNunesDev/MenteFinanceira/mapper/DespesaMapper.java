package PedroNunesDev.MenteFinanceira.mapper;

import PedroNunesDev.MenteFinanceira.dto.request.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.DespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, CategoriaMapper.class})
public interface DespesaMapper {

    Despesa toModel(DespesaDTORequest despesaDTORequest);

    DespesaDtoResponse toDTO(Despesa despesa);
}
