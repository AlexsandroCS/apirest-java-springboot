package io.github.AlexsandroCS.TesteTecKR.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.criarUsuarioDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.retornoCriarUsuarioDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.entity.Usuario;
import io.github.AlexsandroCS.TesteTecKR.domain.repository.UsuarioRepository;
import io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.saidaTokenDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.tokenValidadoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public retornoCriarUsuarioDTO criarUsuario(criarUsuarioDTO novaConta){
        Usuario verificaUsuario = usuarioRepository.findByEmail(novaConta.email());
        if (verificaUsuario != null){
            throw new IllegalArgumentException("Esse E-mail já foi cadastrado!");
        }

        Usuario usuario = new Usuario(null, novaConta.nome(), novaConta.email(), passwordEncoder.encode(novaConta.senha()), new Date());

        usuarioRepository.save(usuario);

        retornoCriarUsuarioDTO usuarioRetorno = new retornoCriarUsuarioDTO(
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getDataCriacao()
        );

        return usuarioRetorno;
    }

    public retornoCriarUsuarioDTO capturarUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        retornoCriarUsuarioDTO retornaDadosNaoSensiveis = new retornoCriarUsuarioDTO(
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getDataCriacao()
        );

        return retornaDadosNaoSensiveis;
    }

    public retornoCriarUsuarioDTO alterarUsuario(Long idUsuario, criarUsuarioDTO atualizaUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario).map(alterarUsuario -> {
            alterarUsuario.setNome(atualizaUsuario.nome());
            alterarUsuario.setEmail(atualizaUsuario.email());
            alterarUsuario.setSenha(passwordEncoder.encode(atualizaUsuario.senha()));
            alterarUsuario.setDataCriacao(new Date());
            return usuarioRepository.save(alterarUsuario);
        }).orElseThrow(() -> new IllegalArgumentException("Usuário não pode ser editado!"));

        retornoCriarUsuarioDTO usuarioAtualizadoRetorno = new retornoCriarUsuarioDTO(
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getDataCriacao()
        );

        return usuarioAtualizadoRetorno;
    }

    public Boolean deletaUsuario(Long idUsuario){
        return usuarioRepository.findById(idUsuario).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }

    private final String TOKEN_CHAVE1 = "AlexsandroCS";
    private final String TOKEN_CHAVE2 = "CSordnasxelA";

    @Transactional
    public saidaTokenDTO verificaLogin(String email, String senha){
        Usuario verificaUsuario = usuarioRepository.findByEmail(email);

        if (verificaUsuario == null){
            throw new IllegalArgumentException("Email inválido!");
        }

        boolean verificaSenha = passwordEncoder.matches(senha, verificaUsuario.getSenha());

        if (verificaSenha == false){
            throw new IllegalArgumentException("Senha inválida!");
        }

        final String geraToken = this.criarToken(verificaUsuario);

        return new saidaTokenDTO(geraToken);
    }

    private String criarToken(Usuario usuario){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(TOKEN_CHAVE1);
            final var token = JWT.create()
                    .withIssuer(TOKEN_CHAVE2)
                    .withSubject(usuario.getEmail())
                    .withClaim("idUsuario", usuario.getId())
                    .withClaim("nomeUsuario", usuario.getNome())
                    .withExpiresAt(Instant.now().plusSeconds(60 * 60 * 1))
                    .sign(algoritmo);
            return token;
        }
        catch (IllegalArgumentException error){
            throw new IllegalArgumentException(error.getMessage());
        }
        catch (JWTCreationException error){
            throw new IllegalArgumentException(error.getMessage());
        }
    }

    @Transactional
    public tokenValidadoDTO validacaoToken(String token){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(TOKEN_CHAVE1);
            JWTVerifier verificador = JWT.require(algoritmo).withIssuer(TOKEN_CHAVE2).build();
            DecodedJWT decodificarToken = verificador.verify(token);

            validarDados(decodificarToken.getSubject(), "Subject");
            validarDados(decodificarToken.getClaim("idUsuario").asInt(), "id");
            validarDados(decodificarToken.getClaim("nomeUsuario").asString(), "nome");

            return new tokenValidadoDTO(true, decodificarToken.getClaim("idUsuario").asInt(),decodificarToken.getClaim("nomeUsuario").asString());
        }
        catch (JWTVerificationException error) {
            throw new IllegalArgumentException("Token inválido ou expirado!");
        }
        catch (IllegalArgumentException error) {
            throw new IllegalArgumentException(error.getMessage());
        }
        catch (Exception error) {
            throw new IllegalArgumentException("Ocorreu algum erro não identificado na validação do Token!");
        }
    }

    private void validarDados(Object dados, String dadosNome) {
        if (dados == null || (dados instanceof String && ((String) dados).isEmpty())) {
            throw new IllegalArgumentException(dadosNome + " do token está ausente ou inválido.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String usuarioNome) throws UsernameNotFoundException {
        Usuario verificaNome = usuarioRepository.findByNome(usuarioNome);
        if (verificaNome.getNome() != null){
            return verificaNome;
        }
        throw new IllegalArgumentException("Usuário não foi encontrado!");
    }
}