package PedroNunesDev.MenteFinanceira.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshDtoRequest(@NotBlank String token) {
}
