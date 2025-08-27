package com.luanmarcene.model;

public class ErroEeProf {

    private String tipo;
    private Integer codErro;
    private String descricaoErro;
    private Long codEventoPR;
    private Long codEventoNA;
    private String nomeResponsavel;
    private String cpfCnpjResponsavel;
    private Long codProdutoNA;
    private Long codProdutoPR;
    private Integer qtdMaxParticipantes;
    private Integer qtdParticipantesAtivos;
    private Integer qtdDisponivel;
    private Long codRealizacaoNA;

    public ErroEeProf() {

    }

    public ErroEeProf(String tipo, Integer codErro, String descricaoErro, Long codEventoPR, Long codEventoNA,
            String nomeResponsavel, String cpfCnpjResponsavel, Long codProdutoNA, Long codProdutoPR,
            Integer qtdMaxParticipantes, Integer qtdParticipantesAtivos, Integer qtdDisponivel, Long codRealizacaoNA) {
        this.tipo = tipo;
        this.codErro = codErro;
        this.descricaoErro = descricaoErro;
        this.codEventoPR = codEventoPR;
        this.codEventoNA = codEventoNA;
        this.nomeResponsavel = nomeResponsavel;
        this.cpfCnpjResponsavel = cpfCnpjResponsavel;
        this.codProdutoNA = codProdutoNA;
        this.codProdutoPR = codProdutoPR;
        this.qtdMaxParticipantes = qtdMaxParticipantes;
        this.qtdParticipantesAtivos = qtdParticipantesAtivos;
        this.qtdDisponivel = qtdDisponivel;
        this.codRealizacaoNA = codRealizacaoNA;
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

    public Integer getQtdMaxParticipantes() {
        return qtdMaxParticipantes;
    }

    public void setQtdMaxParticipantes(Integer qtdMaxParticipantes) {
        this.qtdMaxParticipantes = qtdMaxParticipantes;
    }

    public Integer getQtdParticipantesAtivos() {
        return qtdParticipantesAtivos;
    }

    public void setQtdParticipantesAtivos(Integer qtdParticipantesAtivos) {
        this.qtdParticipantesAtivos = qtdParticipantesAtivos;
    }

    public Integer getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(Integer qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    public Long getCodRealizacaoNA() {
        return codRealizacaoNA;
    }

    public void setCodRealizacaoNA(Long codRealizacaoNA) {
        this.codRealizacaoNA = codRealizacaoNA;
    }

}
