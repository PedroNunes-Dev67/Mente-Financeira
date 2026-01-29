package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.CategoriaDTO;
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

    public Categoria findById(Long id){

        return categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

    }

    public List<Categoria> findAll(){

        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria createCategoria(CategoriaDTO categoriaDTO){

        if (categoriaRepository.findByNome(categoriaDTO.nome()).isPresent()) throw new ConflitoRecursosException("Categoria já cadastrada");

        Categoria categoria = new Categoria(categoriaDTO.nome());

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria updateCategoria(Long id, CategoriaDTO categoriaDTO){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (categoria.getNome().equals(categoriaDTO.nome())) throw new ConflitoRecursosException("Nome da categoria deve ser diferente da anterior");

        categoria.setNome(categoriaDTO.nome());

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deleteCategoria(Long id){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException());

        categoriaRepository.delete(categoria);
    }
}
