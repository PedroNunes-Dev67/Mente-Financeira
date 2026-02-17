package PedroNunesDev.MenteFinanceira.mapper;

import PedroNunesDev.MenteFinanceira.dto.response.TokenVerificacaoDtoResponse;
import PedroNunesDev.MenteFinanceira.model.TokenVerificacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenVerificacaoMapper {

    TokenVerificacaoDtoResponse toDTO(TokenVerificacao tokenVerificacao);
}
