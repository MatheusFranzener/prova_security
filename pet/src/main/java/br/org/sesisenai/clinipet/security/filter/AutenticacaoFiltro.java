package br.org.sesisenai.clinipet.security.filter;

import br.org.sesisenai.clinipet.model.entity.Pessoa;
import br.org.sesisenai.clinipet.repository.PessoaRepository;
import br.org.sesisenai.clinipet.security.model.entities.UserJpa;
import br.org.sesisenai.clinipet.security.service.JpaService;
import br.org.sesisenai.clinipet.security.utils.CookieUtils;
import br.org.sesisenai.clinipet.security.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class AutenticacaoFiltro extends OncePerRequestFilter {

    private CookieUtils cookieUtils;
    private TokenUtils tokenUtils;
    private JpaService jpaService;
    private PessoaRepository pessoaRepository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/logout")
                || (request.getRequestURI().contains("/api/servico") && request.getMethod().equals("GET"))
                || (request.getRequestURI().contains("/api/veterinario") && request.getMethod().equals("GET"))) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = cookieUtils.getTokenCookie(request);
            tokenUtils.validarToken(token);

            Pessoa pessoa = pessoaRepository.findByEmail(tokenUtils.getPessoaEmail(token));

            UserJpa userJpa = new UserJpa(pessoa);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userJpa.getUsername(), userJpa.getPassword(), userJpa.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


}
