package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.PagamentoDespesa;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;

import java.math.BigDecimal;
import java.util.List;

public class DespesaDtoResponse{

    Long idDespesa;
    String titulo;
    BigDecimal valor;
    TipoDespesa tipoDespesa;
    DespesaStatus despesaStatus;
    UsuarioDTOResponse usuario;
    CategoriaDtoResponse categoria;
    List<PagamentoDespesa> pagamentoDespesa;

    public DespesaDtoResponse(Despesa despesa, UsuarioDTOResponse usuario, CategoriaDtoResponse categoria){

        this.idDespesa = despesa.getIdDespesa();
        this.titulo = despesa.getTitulo();
        this.valor = despesa.getValor();
        this.tipoDespesa = despesa.getTipoDespesa();
        this.despesaStatus = despesa.getDespesaStatus();
        this.usuario = usuario;
        this.categoria = categoria;
        this.pagamentoDespesa = despesa.getPagamentos();
    }
}
