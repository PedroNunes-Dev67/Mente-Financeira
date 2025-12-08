package PedroNunesDev.MenteFinanceira.service;

import PedroNunesDev.MenteFinanceira.dto.LoginDTO;
import PedroNunesDev.MenteFinanceira.dto.SenhaDTO;
import PedroNunesDev.MenteFinanceira.dto.UsuarioDTORequest;
import PedroNunesDev.MenteFinanceira.dto.UsuarioDTOResponse;
import PedroNunesDev.MenteFinanceira.model.Usuario;
import PedroNunesDev.MenteFinanceira.model.enums.UsuarioRole;
import PedroNunesDev.MenteFinanceira.repository.UsuarioRepository;
import PedroNunesDev.MenteFinanceira.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public UsuarioDTOResponse findById(Long id){

         return repository.findById(id)
                .map(usuario -> {
                    return new UsuarioDTOResponse(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                    );
                }).orElseThrow(() -> new RuntimeException());
    }

    public List<UsuarioDTOResponse> findAll(){

        List<UsuarioDTOResponse> list = new ArrayList<>();

         list = repository.findAll()
                 .stream().map(usuario -> {
                     return new UsuarioDTOResponse(
                             usuario.getId(),
                             usuario.getNome(),
                             usuario.getEmail()
                     );
                 }).toList();

         return list;
    }

    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest){

        if (repository.findByEmail(usuarioDTORequest.email()) != null) return null;

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTORequest.senha());
        Usuario usuario = new Usuario(usuarioDTORequest.nome(), usuarioDTORequest.email(), senhaCriptografada, UsuarioRole.USUARIO);

        repository.save(usuario);
        return new UsuarioDTOResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public String login(LoginDTO loginDTO){

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.genereteToken((Usuario) authentication.getPrincipal());

        return token;
    }

    public UsuarioDTOResponse updateUsuario(SenhaDTO senhaDTO, Long id) {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();


        return repository.findById(id)
                .filter(usuario -> !bcrypt.matches(senhaDTO.senha(), usuario.getSenha()))
                .map(usuario -> {
                    usuario.setSenha(senhaDTO.senha());
                    repository.save(usuario);

                    return new UsuarioDTOResponse(
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getEmail()
                    );
                }).orElseThrow(() -> new RuntimeException());
    }

    public void delete(Long id){

        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException());

        repository.delete(usuario);
    }
}
