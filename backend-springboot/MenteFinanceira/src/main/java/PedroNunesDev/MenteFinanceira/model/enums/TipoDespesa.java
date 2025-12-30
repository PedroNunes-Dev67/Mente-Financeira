package PedroNunesDev.MenteFinanceira.model.enums;

public enum TipoDespesa {

    RECORRENTE("recorrente"),
    NAO_RECORRENTE("nao_recorrente");

    private String tipo;

    TipoDespesa(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }
}
