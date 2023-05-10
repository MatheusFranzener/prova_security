package br.org.sesisenai.clinipet.security.service;

import br.org.sesisenai.clinipet.model.entity.Pessoa;
import br.org.sesisenai.clinipet.repository.AtendenteRepository;
import br.org.sesisenai.clinipet.repository.ClienteRepository;
import br.org.sesisenai.clinipet.repository.PessoaRepository;
import br.org.sesisenai.clinipet.repository.VeterinarioRepository;
import br.org.sesisenai.clinipet.security.model.entities.UserJpa;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pessoa pessoaOptional = pessoaRepository.findByEmail(username);

        if(pessoaOptional == null){
            throw new UsernameNotFoundException("Pessoa não encontrada!");
        }

        return new UserJpa(pessoaOptional);
    }

}
