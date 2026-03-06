package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.DespesaDTORequest;
import PedroNunesDev.MenteFinanceira.dto.response.DespesaDtoResponse;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.mapper.DespesaMapper;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Despesa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.DespesaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.TipoDespesa;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.DespesaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class DespesaService {

    private DespesaRepository despesaRepository;
    private CategoriaRepository categoriaRepository;
    private AuthService authService;
    private DespesaMapper despesaMapper;

    public DespesaService(DespesaRepository despesaRepository, CategoriaRepository categoriaRepository, AuthService authService, DespesaMapper despesaMapper) {
        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
        this.authService = authService;
        this.despesaMapper = despesaMapper;
    }

    @Transactional
    public DespesaDtoResponse cadastrarDespesa(DespesaDTORequest despesaDTORequest){

        Usuario usuario = authService.me();

        Categoria categoria = categoriaRepository.findById(despesaDTORequest.idCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Despesa despesa = new Despesa(
                despesaDTORequest.titulo(),
                despesaDTORequest.valor(),
                TipoDespesa.from(despesaDTORequest.tipoDespesa()),
                usuario,
                despesaDTORequest.dataVencimento(),
                despesaDTORequest.parcelasTotais(),
                categoria);

        Despesa despesaSalva = despesaRepository.save(despesa);

        return despesaMapper.toDTO(despesaSalva);
    }

    @Transactional
    public void deletarDespesa(Long idDespesa){

        Usuario usuario = authService.me();

        Optional<Despesa> despesa = despesaRepository.findByIdDespesaAndUsuario(idDespesa,usuario);

        if (despesa.isEmpty()) throw new ResourceNotFoundException("Despesa não encontrada");
        else{
            despesaRepository.delete(despesa.get());
        }
    }

    @Transactional
    public DespesaDtoResponse atualizarDespesa(Long idDespesa, DespesaDTORequest despesaDTORequest){

        Usuario usuario = authService.me();

        Despesa despesa = despesaRepository.findByIdDespesaAndUsuario(idDespesa,usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));

        despesa = atualizarDadosDespesa(despesa, despesaDTORequest);

        return despesaMapper.toDTO(despesa);
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> buscarDespesasPorUsuario(int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByUsuario(usuario, criarPageable(pagina,items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorCategoria(Long id, int pagina,int items){

        Usuario usuario = authService.me();

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Page<Despesa> paginacaoCriada = despesaRepository.findByUsuarioAndCategoria(usuario,categoria, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorTipo(String tipoDespesa, int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuario(TipoDespesa.from(tipoDespesa), usuario, criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorStatus(String despesaStatus, int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> despesasBuscadas = despesaRepository.findByDespesaStatusAndUsuario(DespesaStatus.from(despesaStatus), usuario, criarPageable(pagina,items));

        Page<DespesaDtoResponse> despesasBuscadasDto = converterParaDTO(despesasBuscadas);

        return despesasBuscadasDto;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorTipoEhStatus(String tipoDespesa, String statusDespesa,int pagina, int items){

        Usuario usuario = authService.me();

        Page<Despesa> paginacaoCriada = despesaRepository.findByTipoDespesaAndUsuarioAndDespesaStatus(TipoDespesa.from(tipoDespesa), usuario, DespesaStatus.from(statusDespesa), criarPageable(pagina, items));

        Page<DespesaDtoResponse> paginacaoDeRespostaDTO = converterParaDTO(paginacaoCriada);

        return paginacaoDeRespostaDTO;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorData(LocalDate dataInicial, LocalDate dataFinal, int pagina, int items){

        validarDatas(dataInicial,dataFinal);

        Usuario usuario = authService.me();

        LocalDateTime dataInicialFormatada = dataInicial.atStartOfDay();
        LocalDateTime dataFinalFormatada = dataFinal.atStartOfDay();

        Page<Despesa> despesasBuscadas = despesaRepository.findByDespesasPorData(dataInicialFormatada,dataFinalFormatada,usuario, criarPageable(pagina,items));

        Page<DespesaDtoResponse> despesasBuscadasDto = converterParaDTO(despesasBuscadas);

        return despesasBuscadasDto;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorDataEhTipo(LocalDate dataInicial, LocalDate dataFinal, String tipoDespesa, int pagina, int items){

        validarDatas(dataInicial,dataFinal);

        Usuario usuario = authService.me();

        LocalDateTime dataInicialFormatada = dataInicial.atStartOfDay();
        LocalDateTime dataFinalFormatada = dataFinal.atStartOfDay();

        Page<Despesa> despesasBuscadas = despesaRepository.findByDespesaPorDataAndTipo(dataInicialFormatada,dataFinalFormatada,usuario,TipoDespesa.from(tipoDespesa), criarPageable(pagina,items));

        Page<DespesaDtoResponse> despesasBuscadasDto = converterParaDTO(despesasBuscadas);

        return despesasBuscadasDto;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorDataEhDespesaStatus(LocalDate dataInicial, LocalDate dataFinal, String despesaStatus, int pagina, int items){

        validarDatas(dataInicial,dataFinal);

        Usuario usuario = authService.me();

        LocalDateTime dataInicialFormatada = dataInicial.atStartOfDay();
        LocalDateTime dataFinalFormatada = dataFinal.atStartOfDay();

        Page<Despesa> despesasBuscadas = despesaRepository.findByDespesaPorDataAndDespesaStatus(dataInicialFormatada,dataFinalFormatada,usuario,DespesaStatus.from(despesaStatus), criarPageable(pagina,items));

        Page<DespesaDtoResponse> despesasBuscadasDto = converterParaDTO(despesasBuscadas);

        return despesasBuscadasDto;
    }

    @Transactional(readOnly = true)
    public Page<DespesaDtoResponse> despesasPorDataEhTipoDespesaEhDespesaStatus(LocalDate dataInicial, LocalDate dataFinal, String tipoDespesa, String despesaStatus, int pagina, int items){

        validarDatas(dataInicial,dataFinal);

        Usuario usuario = authService.me();

        LocalDateTime dataInicialFormatada = dataInicial.atStartOfDay();
        LocalDateTime dataFinalFormatada = dataFinal.atStartOfDay();

        Page<Despesa> despesasBuscadas = despesaRepository.findByDespesaPorDataAndTipoDespesaAndDespesaStatus(dataInicialFormatada,dataFinalFormatada,usuario, TipoDespesa.from(tipoDespesa),DespesaStatus.from(despesaStatus), criarPageable(pagina,items));

        Page<DespesaDtoResponse> despesasBuscadasDto = converterParaDTO(despesasBuscadas);

        return despesasBuscadasDto;
    }

    //Métodos responsaveis por verificar parâmentros de páginação
    private int normalizaPagina(int pagina){

        //Página precisa ser maior ou igual a 0
        return Math.max(pagina,0);
    }

    private int normalizaTamanhoDePagina(int items){

        //Items de uma página devem ser maior que 0 e menor que 10
        return Math.min(Math.max(items,1),10);
    }

    private Page<DespesaDtoResponse> converterParaDTO(Page<Despesa> paginacaoCriada){

        return paginacaoCriada
                .map(despesa -> {

                    return despesaMapper.toDTO(despesa);
                });
    }

    private PageRequest criarPageable(int pagina, int items){

        //Faz normalização da pagina e items por página
        return PageRequest.of(normalizaPagina(pagina), normalizaTamanhoDePagina(items), Sort.by("dataCriacao").descending());
    }

    private Despesa atualizarDadosDespesa(Despesa despesa, DespesaDTORequest despesaDTORequest){

        Categoria categoria = categoriaRepository.findById(despesaDTORequest.idCategoria())
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        despesa.setTitulo(despesaDTORequest.titulo());
        despesa.setValor(despesaDTORequest.valor());
        despesa.setTipoDespesa(TipoDespesa.from(despesaDTORequest.tipoDespesa()));
        despesa.setDataVencimento(despesaDTORequest.dataVencimento());
        despesa.setParcelasTotais(despesaDTORequest.parcelasTotais());
        despesa.setCategoria(categoria);

        despesa.analisarParcelas();

        despesaRepository.save(despesa);
        return despesa;
    }

    private void validarDatas(LocalDate dataInicial,LocalDate dataFinal){

        if (dataInicial.isAfter(dataFinal)) throw new IllegalArgumentException("Data inicial não pode ser posterior a data final");

        boolean intervaloDeMeses = ChronoUnit.MONTHS.between(dataInicial,dataFinal) > 3;

        if (intervaloDeMeses) throw new IllegalArgumentException("Intervalo de meses superior ao suportado, busca de até 3 meses permitido");
    }
}