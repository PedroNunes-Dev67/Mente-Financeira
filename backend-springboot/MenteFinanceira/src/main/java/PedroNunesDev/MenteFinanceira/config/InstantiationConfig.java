package PedroNunesDev.MenteFinanceira.config;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Financa;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.FinancaStatus;
import PedroNunesDev.MenteFinanceira.model.enums.UsuarioRole;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.FinancaRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
public class InstantiationConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private FinancaRepository financaRepository;

    @Override
    public void run(String... args) throws Exception {

        financaRepository.deleteAll();
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();

        String senha = new BCryptPasswordEncoder().encode("1234");

        Usuario usuario1 = new Usuario("Pedro Nunes", "pedro@gmail.com", senha, UsuarioRole.ADMIN);

        usuarioRepository.save(usuario1);

        Categoria categoria1 = new Categoria("Saúde");
        Categoria categoria2 = new Categoria("Alimentação");
        Categoria categoria3 = new Categoria("Despesas mensais");
        Categoria categoria4 = new Categoria("Planos/assinaturas");

        categoriaRepository.saveAll(Arrays.asList(categoria1,categoria2,categoria3,categoria4));

        Financa financa1 = new Financa("Academia", 75.0, FinancaStatus.PENDENTE, 15, usuario1, categoria1);
        Financa financa2 = new Financa("HBO Max", 29.9, FinancaStatus.PENDENTE, 22, usuario1, categoria4);

        financaRepository.saveAll(Arrays.asList(financa1,financa2));

    }
}
