package com.luanmarcene.model;

public class ErroEeParticipante {

    private String tipo;
    private String cpfCnpjParticipante;
    private String nomeParticipante;
    private Integer codErro;
    private String descricaoErro;
    private Long codEventoNA;
    private Long codEventoPR;
    private String nomeResponsavel;
    private String cpfCnpjResponsavel;
    private Long codProdutoNA;
    private Long codProdutoPR;

    public ErroEeParticipante() {

    }

    public ErroEeParticipante(String tipo, String cpfCnpjParticipante, String nomeParticipante, Integer codErro,
            String descricaoErro, Long codEventoNA, Long codEventoPR, String nomeResponsavel, String cpfCnpjResponsavel,
            Long codProdutoNA,
            Long codProdutoPR) {
        this.tipo = tipo;
        this.cpfCnpjParticipante = cpfCnpjParticipante;
        this.nomeParticipante = nomeParticipante;
        this.codErro = codErro;
        this.descricaoErro = descricaoErro;
        this.codEventoNA = codEventoNA;
        this.codEventoPR = codEventoPR;
        this.nomeResponsavel = nomeResponsavel;
        this.cpfCnpjResponsavel = cpfCnpjResponsavel;
        this.codProdutoNA = codProdutoNA;
        this.codProdutoPR = codProdutoPR;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCpfCnpjParticipante() {
        return cpfCnpjParticipante;
    }

    public void setCpfCnpjParticipante(String cpfCnpjParticipante) {
        this.cpfCnpjParticipante = cpfCnpjParticipante;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public Integer getCodErro() {
        return codErro;
    }

    public void setCodErro(Integer codErro) {
        this.codErro = codErro;
    }

    public Long getCodEventoNA() {
        return codEventoNA;
    }

    public void setCodEventoNA(Long codEventoNA) {
        this.codEventoNA = codEventoNA;
    }

    public Long getCodEventoPR() {
        return codEventoPR;
    }

    public void setCodEventoPR(Long codEventoPR) {
        this.codEventoPR = codEventoPR;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getCpfCnpjResponsavel() {
        return cpfCnpjResponsavel;
    }

    public void setCpfCnpjResponsavel(String cpfCnpjResponsavel) {
        this.cpfCnpjResponsavel = cpfCnpjResponsavel;
    }

    public Long getCodProdutoNA() {
        return codProdutoNA;
    }

    public void setCodProdutoNA(Long codProdutoNA) {
        this.codProdutoNA = codProdutoNA;
    }

    public Long getCodProdutoPR() {
        return codProdutoPR;
    }

    public void setCodProdutoPR(Long codProdutoPR) {
        this.codProdutoPR = codProdutoPR;
    }

    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

}
