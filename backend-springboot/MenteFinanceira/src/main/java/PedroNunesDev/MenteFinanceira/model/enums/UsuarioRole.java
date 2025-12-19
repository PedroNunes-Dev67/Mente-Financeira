package PedroNunesDev.MenteFinanceira.model.enums;

public enum UsuarioRole {

    USUARIO("usuario"),
    ADMIN("admin");

    private String role;

    UsuarioRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
