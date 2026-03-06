package PedroNunesDev.MenteFinanceira.dto.response;

public record LoginDtoResponse(
        String acessToken,
        String refreshToken
) {
}
