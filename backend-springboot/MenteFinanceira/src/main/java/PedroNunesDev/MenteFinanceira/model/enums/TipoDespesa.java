package PedroNunesDev.MenteFinanceira.model.enums;

import java.util.Arrays;

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

    public static TipoDespesa from(String tipoBuscado){

        TipoDespesa tipoDespesa = null;

        for (TipoDespesa t : values()){
            if (t.tipo.equals(tipoBuscado)){
                tipoDespesa = t;
            }
        }

        if (tipoDespesa == null){
            throw new IllegalArgumentException("Tipo de despesa incorreto");
        }

        return tipoDespesa;
    }
}
