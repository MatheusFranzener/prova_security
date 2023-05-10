package br.org.sesisenai.clinipet.security.utils;

import br.org.sesisenai.clinipet.model.entity.Pessoa;
import br.org.sesisenai.clinipet.model.entity.Veterinario;
import br.org.sesisenai.clinipet.security.model.entities.UserJpa;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CookieUtils {
    private final TokenUtils tokenUtils = new TokenUtils();

    public Cookie gerarTokenCookie(Pessoa pessoa) {
        String token = tokenUtils.gerarToken(pessoa);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(2700);
        return cookie;
    }

    public String getTokenCookie(HttpServletRequest request) {
        try {
            Cookie cookie = WebUtils.getCookie(request, "token");
            return cookie.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void logoutExcluirCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "token");
        cookie.setMaxAge(0);
    }

}
