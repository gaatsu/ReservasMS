package com.reservasala.reserva.interfaces.rest.dto;

public class ReservaResponse {
    private Long id;
    private Long salaId;
    private Long usuarioId;
    private String dataHora;

    public ReservaResponse(Long id, Long salaId, Long usuarioId, String dataHora) {
        this.id = id;
        this.salaId = salaId;
        this.usuarioId = usuarioId;
        this.dataHora = dataHora;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSalaId() { return salaId; }
    public void setSalaId(Long salaId) { this.salaId = salaId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
} 