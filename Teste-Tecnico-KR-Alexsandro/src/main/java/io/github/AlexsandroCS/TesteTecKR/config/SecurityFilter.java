package io.github.AlexsandroCS.TesteTecKR.config;

import io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.tokenValidadoDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private UsuarioService usuarioService;

    public SecurityFilter(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenAutorizado = request.getHeader("Authorization");

        if (tokenAutorizado != null){
            String tokenAut = tokenAutorizado.replace("Bearer ","");
            tokenValidadoDTO usuarioNomeAut = this.usuarioService.validacaoToken(tokenAut);
            UserDetails usuarioNome = this.usuarioService.loadUserByUsername(usuarioNomeAut.userValid());
            UsernamePasswordAuthenticationToken autorizacao = new UsernamePasswordAuthenticationToken(usuarioNome,null,usuarioNome.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autorizacao);
        }
        filterChain.doFilter(request, response);
    }
}
