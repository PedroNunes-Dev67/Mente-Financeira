package PedroNunesDev.MenteFinanceira.dto;

import jakarta.validation.constraints.NotBlank;

public record SenhaDTO(
        @NotBlank String senha) {
}
