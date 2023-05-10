package br.org.sesisenai.clinipet.security.model.entities;


import br.org.sesisenai.clinipet.model.entity.Pessoa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJpa implements UserDetails {

    private Pessoa pessoa;

    private List<Perfil> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private String password;

    private String username;

    public UserJpa(Pessoa pessoa){
        this.pessoa = pessoa;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.password = pessoa.getSenha();
        this.username = pessoa.getEmail();
        this.authorities = new ArrayList<>();
        this.authorities.add(Perfil.perfilOf(pessoa.getClass().getSimpleName()));
    }

}
