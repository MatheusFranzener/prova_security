package br.org.sesisenai.clinipet.security.utils;

import br.org.sesisenai.clinipet.model.entity.Pessoa;
import br.org.sesisenai.clinipet.repository.PessoaRepository;
import br.org.sesisenai.clinipet.security.model.entities.UserJpa;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenUtils {

    private final String senhaForte = "05a9e62653eb0eaa116a1b8bbc06dd30ab0df73ab8ae16a500c80875e6e6c8a9";
    private PessoaRepository pessoaRepository;

    public String gerarToken(Pessoa pessoa) {
        return Jwts.builder()
                .setIssuer("CliniPet")
                .setSubject(pessoa.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 2700000))
                .signWith(SignatureAlgorithm.HS256, senhaForte)
                .compact();
    }

    public String getPessoaEmail(String token) {
        return Jwts.parser()
                .setSigningKey(senhaForte)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(senhaForte).parseClaimsJws(token);
        } catch (Exception e) {
            throw new RuntimeException("Token Inválido!");
        }
    }

}
