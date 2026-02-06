package PedroNunesDev.MenteFinanceira.config;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.UsuarioRole;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
public class InitializartionDataConfig implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Categoria categoria1 = new Categoria("Saúde");
        Categoria categoria2 = new Categoria("Alimentação");
        Categoria categoria3 = new Categoria("Assinaturas");
        Categoria categoria4 = new Categoria("Casa");
        Categoria categoria5 = new Categoria("Compras");
        Categoria categoria6 = new Categoria("Educação");
        Categoria categoria7 = new Categoria("Lazer");
        Categoria categoria8 = new Categoria("Serviços");
        Categoria categoria9 = new Categoria("Supermercado");
        Categoria categoria10 = new Categoria("Transporte");
        Categoria categoria11 = new Categoria("Viagem");
        Categoria categoria12 = new Categoria("Economias");
        Categoria categoria13 = new Categoria("Outros");

        categoriaRepository.saveAll(Arrays.asList(categoria1,categoria2,categoria3,categoria4,categoria5,categoria6,categoria7,categoria8,categoria9,categoria10,
                categoria11,categoria12,categoria13));

        String senhaCripto = bCryptPasswordEncoder.encode("pedro1234");

        Usuario usuarioAdmin = new Usuario("Pedro Nunes", "pedro@gmail.com", senhaCripto, UsuarioRole.ADMIN);
        usuarioAdmin.setVerificacaoEmail(true);

        usuarioRepository.save(usuarioAdmin);
    }
}
