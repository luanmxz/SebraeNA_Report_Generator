package com.luanmarcene.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luanmarcene.model.Envio;
import com.luanmarcene.model.ErroEeAluno;
import com.luanmarcene.model.ErroEeParticipante;
import com.luanmarcene.model.ErroEeProf;

public class EnviosDAO {

    public List<Envio> getEnviosByDate(String dataInicial, String dataFinal) throws SQLException, ParseException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Envio> envios = new ArrayList<>();

        String sql = "SELECT to_char(tb1.a001_dt_atz, 'DD/MM/YYYY') as data_envio, " +
                "tb1.a000_cod_tipo as cod_tipo, " +
                "CASE " +
                "   WHEN tb1.a000_cod_tipo = 1 THEN 'PF' " +
                "   WHEN tb1.a000_cod_tipo = 2 THEN 'PJ' " +
                "   WHEN tb1.a000_cod_tipo = 3 THEN 'vínculos' " +
                "   WHEN tb1.a000_cod_tipo = 4 THEN 'evento' " +
                "   WHEN tb1.a000_cod_tipo = 5 THEN 'Participante Evento' " +
                "   WHEN tb1.a000_cod_tipo = 6 THEN 'Interação Atendimento' " +
                "   WHEN tb1.a000_cod_tipo = 7 THEN 'instrutor' " +
                "   WHEN tb1.a000_cod_tipo = 8 THEN 'Educação Empreendedora Prof' " +
                "   WHEN tb1.a000_cod_tipo = 9 THEN 'Participante Educação Empreendedora Prof' " +
                "   WHEN tb1.a000_cod_tipo = 10 THEN 'Educação Empreendedora Aluno' " +
                "   WHEN tb1.a000_cod_tipo = 11 THEN 'Consolidação de Eventos\\EE' " +
                "END AS TPDOCIDATENDENTE, count(*) AS total " +
                "FROM integracaosas.tb001_integ_integracao tb1 " +
                "WHERE TRUNC(tb1.a001_dt_atz) BETWEEN ? AND ? " +
                "GROUP BY to_char(tb1.a001_dt_atz, 'DD/MM/YYYY'), tb1.a000_cod_tipo " +
                "ORDER BY 1 DESC, 2 ASC";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(dataInicial);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(dataFinal);

            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            rs = ps.executeQuery();

            while (rs.next()) {
                String dataEnvio = rs.getString("data_envio");
                Integer codTipo = rs.getInt("cod_tipo");
                String tipoEnvio = rs.getString("tpdocidatendente");
                Integer totalEnvios = rs.getInt("total");

                Envio envio = new Envio(dataEnvio, codTipo, tipoEnvio, totalEnvios);
                envios.add(envio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                ConnectionFactory.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return envios;
    }

    public List<Envio> getEnviosComErroByDate(String dataInicial, String dataFinal) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Envio> enviosComErro = new ArrayList<>();

        String sql = "SELECT to_char(tb1.a001_dt_atz, 'DD/MM/YYYY') as data_envio, tb1.a000_cod_tipo as cod_tipo, "
                + "  case "
                + "    when tb1.a000_cod_tipo = 1 THEN 'PF' "
                + "    when tb1.a000_cod_tipo = 2 THEN 'PJ' "
                + "    when tb1.a000_cod_tipo = 3 THEN 'vínculos' "
                + "    when tb1.a000_cod_tipo = 4 THEN 'evento' "
                + "    when tb1.a000_cod_tipo = 5 THEN 'Participante Evento' "
                + "    when tb1.a000_cod_tipo = 6 THEN 'Interação Atendimento' "
                + "    when tb1.a000_cod_tipo = 7 THEN 'instrutor' "
                + "    when tb1.a000_cod_tipo = 8 THEN 'Educação Empreendedora Prof' "
                + "    when tb1.a000_cod_tipo = 9 THEN 'Participante Educação Empreendedora Prof' "
                + "    when tb1.a000_cod_tipo = 10 THEN 'Educação Empreendedora Aluno' "
                + "    when tb1.a000_cod_tipo = 11 THEN 'Consolidação de Eventos\\EE' "
                + "  END AS TPDOCIDATENDENTE, count(*) AS total "
                + "FROM integracaosas.tb001_integ_integracao tb1 "
                + "WHERE tb1.a000_cod_status in (2,4,6) "
                + "  and trunc(tb1.a001_dt_atz) between ? and ? "
                + "  and tb1.a001_dt_atz is not null "
                + "GROUP BY to_char(tb1.a001_dt_atz, 'DD/MM/YYYY'), tb1.a000_cod_tipo "
                + "ORDER BY 1 desc, 2 asc";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(dataInicial);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(dataFinal);

            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            rs = ps.executeQuery();

            while (rs.next()) {
                String dataEnvio = rs.getString("data_envio");
                Integer codTipo = rs.getInt("cod_tipo");
                String tipoEnvio = rs.getString("tpdocidatendente");
                Integer totalEnvios = rs.getInt("total");

                Envio envio = new Envio(dataEnvio, codTipo, tipoEnvio, totalEnvios);
                enviosComErro.add(envio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                ConnectionFactory.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return enviosComErro;
    }

    public List<Envio> getErrosComDescricaoByDate(String dataInicial, String dataFinal) {

        List<Envio> errosDesc = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT to_char(tb1.a001_dt_atz, 'DD/MM/YYYY') as data_envio, tb1.a000_cod_tipo as cod_tipo, dbms_lob.substr(tb2_lob.a002_erro_desc, 200, 1) as erro_desc, "
                + "  case "
                + "    when tb1.a000_cod_tipo = 1 THEN 'PF' "
                + "    when tb1.a000_cod_tipo = 2 THEN 'PJ' "
                + "    when tb1.a000_cod_tipo = 3 THEN 'vínculos' "
                + "    when tb1.a000_cod_tipo = 4 THEN 'evento' "
                + "    when tb1.a000_cod_tipo = 5 THEN 'Participante Evento' "
                + "    when tb1.a000_cod_tipo = 6 THEN 'Interação Atendimento' "
                + "    when tb1.a000_cod_tipo = 7 THEN 'instrutor' "
                + "    when tb1.a000_cod_tipo = 8 THEN 'Educação Empreendedora Prof' "
                + "    when tb1.a000_cod_tipo = 9 THEN 'Participante Educação Empreendedora Prof' "
                + "    when tb1.a000_cod_tipo = 10 THEN 'Educação Empreendedora Aluno' "
                + "    when tb1.a000_cod_tipo = 11 THEN 'Consolidação de Eventos\\EE' "
                + "  END AS TPDOCIDATENDENTE, count(*) AS total "
                + "FROM integracaosas.tb001_Integ_Integracao tb1 "
                + "inner JOIN integracaosas.tb002_integ_integracao_erro tb2 ON tb2.a001_cod_integ = tb1.a001_cod_integ "
                + "inner JOIN integracaosas.tb002_integracao_erro_lob tb2_lob ON tb2_lob.a002_cod_integ_erro = tb2.a002_cod_integ_erro "
                + "WHERE tb1.a000_cod_status in (2,4,6) "
                + "  and trunc(tb1.a001_dt_atz) between ? and ? "
                + "  AND tb2.a002_cod_integ_erro = "
                + "    (SELECT max(tb2_aux.a002_cod_integ_erro) "
                + "       FROM integracaosas.tb002_integ_integracao_erro tb2_aux "
                + "      WHERE tb2_aux.a001_cod_integ = tb1.a001_cod_integ) "
                + "  and tb1.a001_dt_atz is not null "
                + "GROUP BY to_char(tb1.a001_dt_atz, 'DD/MM/YYYY'), tb1.a000_cod_tipo, dbms_lob.substr(tb2_lob.a002_erro_desc, 200, 1) "
                + "ORDER BY 1 desc, 2 asc";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(dataInicial);
            Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(dataFinal);

            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            rs = ps.executeQuery();

            while (rs.next()) {
                String dataEnvio = rs.getString("data_envio");
                Integer codTipo = rs.getInt("cod_tipo");
                String tipoEnvio = rs.getString("tpdocidatendente");
                Integer totalEnvios = rs.getInt("total");
                String descricaoErro = rs.getString("erro_desc");

                Envio envio = new Envio(dataEnvio, codTipo, tipoEnvio, totalEnvios, null, descricaoErro);
                errosDesc.add(envio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                ConnectionFactory.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return errosDesc;
    }

    public List<ErroEeProf> getErrosEducacaoEmpreendedoraProf() {

        List<ErroEeProf> errosEeProf = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT distinct "
                + "  'EDUCAÇÃO EMPREENDEDORA PROF' as tipo, "
                + "  tb2.a002_erro_cod as cod_erro, "
                + "  dbms_lob.substr(tb2_lob.a002_erro_desc, 200, 1) as descricao_erro, "
                + "  eprof.codeventobi as codevento_pr, "
                + "  eprof.codrealizacao_na as codevento_na, "
                + "  TRANSLATE(dc.nm_agente,'áéíóúâêîôûàèìòùäëïöüãõñç','aeiouaeiouaeiouaeiouaonc') as nome_responsavel, "
                + "  eprof.cpfcnpjresponsavel as cpf_cnpj_responsavel, "
                + "  eprof.codproduto as codproduto_NA, "
                + "  dp.cd_produto as codproduto_PR, "
                + "  fe.qt_max_part qtd_max_participantes, "
                + "  fe.qt_part_ativos qtd_participantes_ativos, "
                + "  fe.qt_disponivel qtd_disponivel, "
                + "  eprof.codrealizacao_na "
                + "FROM integracaosas.tb001_Integ_Integracao tb1 "
                + "INNER JOIN integracaosas.tb001_integracao_lob tb1_lob ON tb1_lob.a001_cod_integ = tb1.a001_cod_integ "
                + "INNER JOIN integracaosas.tb002_integ_integracao_erro tb2 ON tb2.a001_cod_integ = tb1.a001_cod_integ "
                + "INNER JOIN integracaosas.tb002_integracao_erro_lob tb2_lob ON tb2_lob.a002_cod_integ_erro = tb2.a002_cod_integ_erro "
                + "INNER JOIN bi_dw.f_envio_na_educ_empreend_prof eprof on eprof.codeventobi = tb1.a001_cod_dado "
                + "INNER JOIN bi_dw.f_evento fe on fe.cd_evento = eprof.codeventobi "
                + "INNER JOIN bi_dw.d_cliente dc on dc.cd_nacional = eprof.responsavel "
                + "INNER JOIN bi_dw.d_produto dp on dp.ch_prod_nacional_sas = eprof.codproduto "
                + "WHERE tb1.a000_cod_status IN (2, 4, 6) "
                + "  AND tb2.a002_cod_integ_erro = "
                + "    (SELECT max(tb2_aux.a002_cod_integ_erro) "
                + "       FROM integracaosas.tb002_integ_integracao_erro tb2_aux "
                + "      WHERE tb2_aux.a001_cod_integ = tb1.a001_cod_integ) "
                + "  AND tb1.a000_cod_tipo IN (8) "
                + "  AND tb2.a002_erro_cod <> 500 "
                + "order by tb2.a002_erro_cod asc";

        try {
            conn = ConnectionFactory.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Integer codErro = rs.getInt("cod_erro");
                String descricaoErro = rs.getString("descricao_erro");
                Long codEventoPR = rs.getLong("codevento_pr");
                Long codEventoNA = rs.getLong("codevento_na");
                String nomeResponsavel = rs.getString("nome_responsavel");
                String cpfCnpjResponsavel = rs.getString("cpf_cnpj_responsavel");
                Long codProdutoNA = rs.getLong("codproduto_NA");
                Long codProdutoPR = rs.getLong("codproduto_PR");
                Integer qtdMaxParticipantes = rs.getInt("qtd_max_participantes");
                Integer qtdParticipantesAtivos = rs.getInt("qtd_participantes_ativos");
                Integer qtdDisponivel = rs.getInt("qtd_disponivel");
                Long codRealizacaoNA = rs.getLong("codrealizacao_na");

                ErroEeProf erroEeProf = new ErroEeProf(tipo, codErro, descricaoErro, codEventoNA, codEventoPR,
                        nomeResponsavel, cpfCnpjResponsavel, codProdutoNA, codProdutoPR,
                        qtdMaxParticipantes, qtdParticipantesAtivos, qtdDisponivel, codRealizacaoNA);

                errosEeProf.add(erroEeProf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                ConnectionFactory.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return errosEeProf;
    }

    public List<ErroEeAluno> getErrosEducacaoEmpreendedoraAluno() {

        List<ErroEeAluno> errosEeAluno = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT distinct "
                + "  'EDUCAÇÃO EMPREENDEDORA ALUNO' as tipo, "
                + "  tb2.a002_erro_cod as cod_erro, "
                + "  dbms_lob.substr(tb2_lob.a002_erro_desc, 200, 1) as descricao_erro, "
                + "  ealun.codeventobi as codevento_pr, "
                + "  ealun.codrealizacao_na as codevento_na, "
                + "  TRANSLATE(dc.nm_agente,'áéíóúâêîôûàèìòùäëïöüãõñç','aeiouaeiouaeiouaeiouaonc') as nome_responsavel, "
                + "  ealun.cpfcnpjresponsavel as cpf_cnpj_responsavel, "
                + "  ealun.codproduto as codproduto_NA, "
                + "  dp.cd_produto as codproduto_PR "
                + "FROM integracaosas.tb001_Integ_Integracao tb1 "
                + "INNER JOIN integracaosas.tb001_integracao_lob tb1_lob ON tb1_lob.a001_cod_integ = tb1.a001_cod_integ "
                + "INNER JOIN integracaosas.tb002_integ_integracao_erro tb2 ON tb2.a001_cod_integ = tb1.a001_cod_integ "
                + "INNER JOIN integracaosas.tb002_integracao_erro_lob tb2_lob ON tb2_lob.a002_cod_integ_erro = tb2.a002_cod_integ_erro "
                + "INNER JOIN bi_dw.f_envio_na_educ_empreend_alun ealun on ealun.codeventobi = tb1.a001_cod_dado "
                + "INNER JOIN bi_dw.d_cliente dc on dc.cd_nacional = ealun.responsavel "
                + "INNER JOIN bi_dw.d_produto dp on dp.ch_prod_nacional_sas = ealun.codproduto "
                + "WHERE tb1.a000_cod_status IN (2, 4, 6) "
                + "  AND tb2.a002_cod_integ_erro = "
                + "    (SELECT max(tb2_aux.a002_cod_integ_erro) "
                + "       FROM integracaosas.tb002_integ_integracao_erro tb2_aux "
                + "      WHERE tb2_aux.a001_cod_integ = tb1.a001_cod_integ) "
                + "  AND tb1.a000_cod_tipo IN (10) "
                + "  AND tb2.a002_erro_cod <> 500 "
                + "order by tb2.a002_erro_cod asc";

        try {
            conn = ConnectionFactory.getConnection();

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Integer codErro = rs.getInt("cod_erro");
                String descricaoErro = rs.getString("descricao_erro");
                Long codEventoNA = rs.getLong("codevento_na");
                Long codEventoPR = rs.getLong("codevento_pr");
                String nomeResponsavel = rs.getString("nome_responsavel");
                String cpfCnpjResponsavel = rs.getString("cpf_cnpj_responsavel");
                Long codProdutoNA = rs.getLong("codproduto_NA");
                Long codProdutoPR = rs.getLong("codproduto_PR");

                ErroEeAluno erroEeAluno = new ErroEeAluno(tipo, codErro, descricaoErro, codEventoNA, codEventoPR,
                        nomeResponsavel, cpfCnpjResponsavel, codProdutoNA, codProdutoPR);
                errosEeAluno.add(erroEeAluno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                ConnectionFactory.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return errosEeAluno;
    }

    public List<ErroEeParticipante> getErrosEducacaoEmpreendedoraParticipante() {

        List<ErroEeParticipante> errosEeParticipante = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT distinct "
                + "  'EDUCAÇÃO EMPREENDEDORA PARTICIPANTE' as tipo, "
                + "  dc_participante.ds_cnpj_cpf_fmt as cpf_cnpj_participante, "
                + "  TRANSLATE(dc_participante.nm_agente,'áéíóúâêîôûàèìòùäëïöüãõñç','aeiouaeiouaeiouaeiouaonc') as nome_participante, "
                + "  tb2.a002_erro_cod as cod_erro, "
                + "  dbms_lob.substr(tb2_lob.a002_erro_desc, 200, 1) as descricao_erro, "
                + "  epart.codrealizacaona as codevento_NA, "
                + "  epart.cd_evento as codevento_PR, "
                + "  TRANSLATE(dc_responsavel.nm_agente,'áéíóúâêîôûàèìòùäëïöüãõñç','aeiouaeiouaeiouaeiouaonc') as nome_responsavel, "
                + "  eprof.cpfcnpjresponsavel as cpf_cnpj_responsavel, "
                + "  eprof.codproduto as codproduto_NA, "
                + "  dp.cd_produto as codproduto_PR "
                + "FROM integracaosas.tb001_Integ_Integracao tb1 "
                + "INNER JOIN integracaosas.tb001_integracao_lob tb1_lob ON tb1_lob.a001_cod_integ = tb1.a001_cod_integ "
                + "INNER JOIN integracaosas.tb002_integ_integracao_erro tb2 ON tb2.a001_cod_integ = tb1.a001_cod_integ "
                + "INNER JOIN integracaosas.tb002_integracao_erro_lob tb2_lob ON tb2_lob.a002_cod_integ_erro = tb2.a002_cod_integ_erro "
                + "INNER JOIN bi_dw.f_envio_na_educ_empr_prof_part epart on epart.cd_evento = tb1.a001_cod_dado "
                + "INNER JOIN bi_dw.f_envio_na_educ_empreend_prof eprof on eprof.codeventobi = epart.cd_evento "
                + "INNER JOIN bi_dw.d_cliente dc_responsavel on dc_responsavel.cd_nacional = eprof.responsavel "
                + "INNER JOIN bi_dw.d_produto dp on dp.ch_prod_nacional_sas = eprof.codproduto "
                + "INNER JOIN bi_dw.d_cliente dc_participante on dc_participante.cd_nacional = TO_NUMBER(REGEXP_SUBSTR(tb1_lob.a001_dado_env, '\"CodCliente\":(\\d+)', 1, 1, NULL, 1)) "
                + "WHERE tb1.a000_cod_status IN (2, 4, 6) "
                + "  AND tb2.a002_cod_integ_erro = "
                + "    (SELECT max(tb2_aux.a002_cod_integ_erro) "
                + "       FROM integracaosas.tb002_integ_integracao_erro tb2_aux "
                + "      WHERE tb2_aux.a001_cod_integ = tb1.a001_cod_integ) "
                + "  AND tb1.a000_cod_tipo IN (9) "
                + "  AND tb2.a002_erro_cod <> 500 "
                + "order by tb2.a002_erro_cod asc";

        try {
            conn = ConnectionFactory.getConnection();

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String cpfCnpjParticipante = rs.getString("cpf_cnpj_participante");
                String nomeResposanvel = rs.getString("nome_participante");
                Integer codErro = rs.getInt("cod_erro");
                String descricaoErro = rs.getString("descricao_erro");
                Long codEventoNA = rs.getLong("codevento_NA");
                Long codEventoPR = rs.getLong("codevento_PR");
                String nomeResponsavel = rs.getString("nome_responsavel");
                String cpfCnpjResponsavel = rs.getString("cpf_cnpj_responsavel");
                Long codProdutoNA = rs.getLong("codproduto_NA");
                Long codProdutoPR = rs.getLong("codproduto_PR");

                ErroEeParticipante erroEeParticipante = new ErroEeParticipante(tipo, cpfCnpjParticipante,
                        nomeResposanvel,
                        codErro, descricaoErro, codEventoNA, codEventoPR,
                        nomeResponsavel, cpfCnpjResponsavel, codProdutoNA, codProdutoPR);

                errosEeParticipante.add(erroEeParticipante);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                ConnectionFactory.closeConnection(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return errosEeParticipante;
    }
}
