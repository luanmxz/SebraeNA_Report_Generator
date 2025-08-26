package com.luanmarcene.model;

public class Envio {

    private String dataEnvio;
    private Integer codTipo;
    private String tipoEnvio;
    private Integer totalEnvios;

    public Envio(String dataEnvio, Integer codTipo, String tipoEnvio, Integer totalEnvios) {
        this.dataEnvio = dataEnvio;
        this.codTipo = codTipo;
        this.tipoEnvio = tipoEnvio;
        this.totalEnvios = totalEnvios;
    }

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Integer getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(Integer codTipo) {
        this.codTipo = codTipo;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Integer getTotalEnvios() {
        return totalEnvios;
    }

    public void setTotalEnvios(Integer totalEnvios) {
        this.totalEnvios = totalEnvios;
    }

}
