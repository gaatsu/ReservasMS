package com.reservasala.usuario.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private Boolean ativo;

    public Usuario(Long id, String nome, String email, String matricula, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.ativo = ativo;
    }
} 