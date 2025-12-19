package PedroNunesDev.MenteFinanceira.model.enums;

public enum FinancaStatus {

    PENDENTE("pendente"),
    PAGO("pago");

    private String status;

    FinancaStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
