package PedroNunesDev.MenteFinanceira.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        @Schema(example = "exemple@gmail.com")
        String email,
        @NotBlank(message = "Senha obrigatória")
        @Schema(example = "exemplo1234")
        String senha) {
}
