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

    public static DespesaStatus from(String status){

        DespesaStatus despesaStatus = null;

        for (DespesaStatus d: values()){
            if (d.status.equals(status)){
                despesaStatus = d;
            }
        }

        if (despesaStatus == null){
            throw new IllegalArgumentException("Status de despesa incorreto");
        }

        return despesaStatus;
    }
}
