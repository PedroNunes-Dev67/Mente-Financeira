package PedroNunesDev.MenteFinanceira.model.enums;

public enum DespesaStatus {

    PENDENTE("pendente"),
    PAGO("pago");

    private String status;

    DespesaStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
