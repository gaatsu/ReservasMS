package com.reservasala.sala.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sala {
    private Long id;
    private String nome;
    private Integer capacidade;
    private String localizacao;
    private Boolean ativa;

    public Sala(Long id, String nome, Integer capacidade, String localizacao, Boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.ativa = ativa;
    }
} 