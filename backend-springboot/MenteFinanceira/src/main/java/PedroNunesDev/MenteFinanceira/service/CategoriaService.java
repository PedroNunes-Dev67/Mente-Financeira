package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.CategoriaDTO;
import PedroNunesDev.MenteFinanceira.dto.response.CategoriaDtoResponse;
import PedroNunesDev.MenteFinanceira.exception.ConflitoRecursosException;
import PedroNunesDev.MenteFinanceira.exception.ResourceNotFoundException;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public CategoriaDtoResponse findById(Long id){

        Categoria categoriaBuscada = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        return new CategoriaDtoResponse(categoriaBuscada);
    }

    @Transactional(readOnly = true)
    public List<CategoriaDtoResponse> findAll(){

        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> {
                    return new CategoriaDtoResponse(categoria);
                }).toList();
    }

    @Transactional
    public CategoriaDtoResponse createCategoria(CategoriaDTO categoriaDTO){

        if (categoriaRepository.findByNome(categoriaDTO.nome()).isPresent()) throw new ConflitoRecursosException("Categoria já cadastrada");

        Categoria categoria = new Categoria(categoriaDTO.nome());

        categoriaRepository.save(categoria);

        return new CategoriaDtoResponse(categoria);
    }

    @Transactional
    public CategoriaDtoResponse updateCategoria(Long id, CategoriaDTO categoriaDTO){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (categoria.getNome().equals(categoriaDTO.nome())) throw new ConflitoRecursosException("Nome da categoria deve ser diferente da anterior");

        categoria.setNome(categoriaDTO.nome());

        categoriaRepository.save(categoria);

        return new CategoriaDtoResponse(categoria);
    }

    @Transactional
    public void deleteCategoria(Long id){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        categoriaRepository.delete(categoria);
    }
}
