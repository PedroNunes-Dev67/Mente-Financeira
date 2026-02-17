package PedroNunesDev.MenteFinanceira.mapper;

import PedroNunesDev.MenteFinanceira.dto.response.PagamentoDespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagamentoDespesaMapper {

    PagamentoDespesaDtoResponse toDTO(PagamentoDespesa pagamentoDespesa);
}
