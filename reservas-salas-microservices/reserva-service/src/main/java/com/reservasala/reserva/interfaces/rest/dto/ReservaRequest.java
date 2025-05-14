package com.reservasala.reserva.interfaces.rest.dto;

public class ReservaRequest {
    private Long salaId;
    private Long usuarioId;
    private String dataHora;

    // getters e setters
    public Long getSalaId() { return salaId; }
    public void setSalaId(Long salaId) { this.salaId = salaId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
} 