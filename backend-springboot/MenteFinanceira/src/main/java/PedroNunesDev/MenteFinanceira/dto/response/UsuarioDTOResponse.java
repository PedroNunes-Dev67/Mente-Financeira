package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.Usuario;

public class UsuarioDTOResponse {

    private Long id;
    private String nome;
    private String email;

    public UsuarioDTOResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }
}
