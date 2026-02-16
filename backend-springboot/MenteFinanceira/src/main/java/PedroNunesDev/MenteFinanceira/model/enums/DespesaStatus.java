package PedroNunesDev.MenteFinanceira.model.enums;

public enum DespesaStatus {

    PENDENTE("pendente"),
    PARCIALMENTE_PAGA("parcialmente_paga"),
    PAGA("paga");

    private String status;

    DespesaStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
