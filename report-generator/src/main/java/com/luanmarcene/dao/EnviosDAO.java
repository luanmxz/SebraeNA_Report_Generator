package com.luanmarcene.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luanmarcene.model.Envio;

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

            rs.close();
            ps.close();
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

    public void getEnviosComErroByDate() {

    }
}
