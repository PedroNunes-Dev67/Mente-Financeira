package PedroNunesDev.MenteFinanceira.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioDTOResponse(
        @Schema(example = "1234")
        Long idUsuario,
        @Schema(example = "pedro")
        String nome,
        @Schema(example = "exemplo@gmail.com")
        String email
) {}
