package br.org.sesisenai.clinipet.security.model.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
    ATENDENTE("Atendente"),
    CLIENTE("Cliente"),
    VETERINARIO("Veterinario");

    private String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public static Perfil perfilOf(String simpleName) {
        return switch(simpleName) {
            case "Atendente" -> ATENDENTE;
            case "Cliente" -> CLIENTE;
            case "Veterinario" -> VETERINARIO;
            default -> null;
        };
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
