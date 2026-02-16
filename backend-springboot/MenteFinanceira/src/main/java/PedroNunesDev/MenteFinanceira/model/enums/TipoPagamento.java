package PedroNunesDev.MenteFinanceira.model.enums;

public enum TipoPagamento {
    PIX("pix"),
    DINHEIRO("dinheiro"),
    BOLETO("boleto"),
    CARTAO_DE_CREDITO("cartao_de_credito"),
    CARTAO_DE_DEBITO("cartao_de_debito");

    private String tipoPagamento;

    TipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public static TipoPagamento from(String tipoBuscado){

        if (tipoBuscado == null) return null;

        TipoPagamento tipoPagamemto = null;

        for (TipoPagamento t : values()){
            if (t.tipoPagamento.equals(tipoBuscado)){
                tipoPagamemto = t;
            }
        }

        if (tipoPagamemto == null){
            throw new IllegalArgumentException("Tipo de despesa incorreto");
        }

        return tipoPagamemto;
    }
}
