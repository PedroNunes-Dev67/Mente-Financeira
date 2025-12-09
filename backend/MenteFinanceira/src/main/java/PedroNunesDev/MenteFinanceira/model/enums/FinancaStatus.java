package PedroNunesDev.MenteFinanceira.model.enums;

public enum FinancaStatus {

    PENDENTE("pendente"),
    ATRASADO("atrasado"),
    PAGO("pago");

    private String status;

    FinancaStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
