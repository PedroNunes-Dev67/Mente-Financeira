package PedroNunesDev.MenteFinanceira.repository;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoraRepository extends JpaRepository<Categoria, Long> {
}
