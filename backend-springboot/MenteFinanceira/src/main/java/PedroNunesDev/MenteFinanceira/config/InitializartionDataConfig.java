package PedroNunesDev.MenteFinanceira.config;

import PedroNunesDev.MenteFinanceira.model.Categoria;
import PedroNunesDev.MenteFinanceira.model.Role;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.repository.CategoriaRepository;
import PedroNunesDev.MenteFinanceira.repository.RoleRepository;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
public class InitializartionDataConfig implements CommandLineRunner {

    private CategoriaRepository categoriaRepository;
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    public InitializartionDataConfig(CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

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

        Role role1 = new Role("ROLE_USUARIO");
        Role role2 = new Role("ROLE_ADMIN");

        roleRepository.saveAll(Arrays.asList(role1,role2));

        String senhaCripto = bCryptPasswordEncoder.encode("exemplo1234");

        Usuario usuarioAdmin = new Usuario("Pedro Nunes", "exemplo@gmail.com", senhaCripto);
        usuarioAdmin.getRoles().addAll(Arrays.asList(role1,role2));
        usuarioAdmin.setVerificacaoEmail(true);

        usuarioRepository.save(usuarioAdmin);
    }
}
