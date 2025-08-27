package com.luanmarcene.model;

public class ErroEeAluno {

    private String tipo;
    private Integer codErro;
    private String descricaoErro;
    private Long codEventoPR;
    private Long codEventoNA;
    private String nomeResponsavel;
    private String cpfCnpjResponsavel;
    private Long codProdutoNA;
    private Long codProdutoPR;

    public ErroEeAluno() {

    }

    public ErroEeAluno(String tipo, Integer codErro, String descricaoErro, Long codEventoPR, Long codEventoNA,
            String nomeResponsavel, String cpfCnpjResponsavel, Long codProdutoNA, Long codProdutoPR) {
        this.tipo = tipo;
        this.codErro = codErro;
        this.descricaoErro = descricaoErro;
        this.codEventoPR = codEventoPR;
        this.codEventoNA = codEventoNA;
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

    public Integer getCodErro() {
        return codErro;
    }

    public void setCodErro(Integer codErro) {
        this.codErro = codErro;
    }

    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

    public Long getCodEventoPR() {
        return codEventoPR;
    }

    public void setCodEventoPR(Long codEventoPR) {
        this.codEventoPR = codEventoPR;
    }

    public Long getCodEventoNA() {
        return codEventoNA;
    }

    public void setCodEventoNA(Long codEventoNA) {
        this.codEventoNA = codEventoNA;
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

}
