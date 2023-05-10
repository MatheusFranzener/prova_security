package br.org.sesisenai.clinipet.security.config;

import br.org.sesisenai.clinipet.repository.PessoaRepository;
import br.org.sesisenai.clinipet.security.filter.AutenticacaoFiltro;
import br.org.sesisenai.clinipet.security.service.JpaService;
import br.org.sesisenai.clinipet.security.utils.CookieUtils;
import br.org.sesisenai.clinipet.security.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class AutenticacaoConfig {

    private JpaService jpaService;
    private PessoaRepository pessoaRepository;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));

        configuration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE"));

        configuration.setAllowCredentials(true);

        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/login")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/api/servico/**", "/api/veterinario/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/agenda/**", "/api/cliente/**").hasAnyAuthority("CLIENTE", "VETERINARIO", "ATENDENTE")
                .requestMatchers(HttpMethod.GET, "/api/prontuario/**").hasAnyAuthority("CLIENTE", "VETERINARIO", "ATENDENTE")
                .requestMatchers(HttpMethod.GET, "/api/atendente/**", "/api/animal/**").hasAnyAuthority("VETERINARIO", "ATENDENTE")

                .requestMatchers(HttpMethod.POST, "/api/prontuario", "/api/servico", "/api/veterinario", "/api/atendente").hasAuthority("VETERINARIO")
                .requestMatchers(HttpMethod.POST, "/api/animal", "/api/cliente", "/api/agenda").hasAuthority("ATENDENTE")

                .requestMatchers(HttpMethod.PUT, "/api/prontuario/**", "/api/servico/**", "/api/veterinario/**", "/api/atendente/**").hasAuthority("VETERINARIO")
                .requestMatchers(HttpMethod.PUT, "/api/animal/**", "/api/cliente/**", "/api/agenda/**").hasAnyAuthority("VETERINARIO", "ATENDENTE")

                .requestMatchers(HttpMethod.DELETE, "/api/prontuario/**", "/api/servico/**", "/api/veterinario/**", "/api/atendente/**").hasAuthority("VETERINARIO")
                .requestMatchers(HttpMethod.DELETE, "/api/animal/**", "/api/cliente/**", "/api/agenda/**").hasAnyAuthority("VETERINARIO", "ATENDENTE")
                .anyRequest().authenticated();

        http.csrf().disable();

        http.cors().configurationSource(corsConfigurationSource());

        http.logout()
                .deleteCookies("token")
                .permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new AutenticacaoFiltro(new CookieUtils(), new TokenUtils(), jpaService, pessoaRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
