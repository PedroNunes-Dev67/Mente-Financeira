package PedroNunesDev.MenteFinanceira.dto.response;

import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DespesaDtoResponse {

    private Long idDespesa;
    private String titulo;
    private BigDecimal valor;
    private TipoDespesa tipoDespesa;
    private DespesaStatus despesaStatus;
    private Integer dataVencimento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Integer parcelasTotais;
    private Integer parcelasPagas;
    private UsuarioDTOResponse usuario;
    private CategoriaDtoResponse categoria;

    public DespesaDtoResponse(Despesa despesa, UsuarioDTOResponse usuarioDTOResponse, CategoriaDtoResponse categoria){
        this.idDespesa = despesa.getIdDespesa();
        this.titulo = despesa.getTitulo();
        this.valor = despesa.getValor();
        this.tipoDespesa = despesa.getTipoDespesa();
        this.despesaStatus = despesa.getDespesaStatus();
        this.dataCriacao = despesa.getDataCriacao();
        this.dataAtualizacao = despesa.getDataAtualizacao();
        this.parcelasTotais = despesa.getParcelasTotais();
        this.parcelasPagas = despesa.getParcelasPagas();
        this.usuario = usuarioDTOResponse;
        this.categoria = categoria;
    }
}



