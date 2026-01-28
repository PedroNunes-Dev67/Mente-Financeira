package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.request.CategoriaDTO;
import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Long id){

        return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException());

    }

    public List<Categoria> findAll(){

        return categoriaRepository.findAll();
    }

    public Categoria createCategoria(CategoriaDTO categoriaDTO){

        if (categoriaRepository.findByNome(categoriaDTO.nome()).isPresent()) throw new RuntimeException();

        Categoria categoria = new Categoria(categoriaDTO.nome());

        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, CategoriaDTO categoriaDTO){

        return categoriaRepository.findById(id)
                .filter(categoria -> !categoria.getNome().equals(categoriaDTO.nome()))
                .map(categoria -> {
                    categoria.setNome(categoriaDTO.nome());
                    return categoriaRepository.save(categoria);
                }).orElseThrow(() -> new RuntimeException());
    }

    public void deleteCategoria(Long id){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException());

        categoriaRepository.delete(categoria);
    }
}
