package com.luanmarcene.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.luanmarcene.dao.EnviosDAO;
import com.luanmarcene.model.Envio;

public class EnviosService {

    public List<Envio> getEnviosByDate() throws SQLException, ParseException {
        String dataAtual = LocalDate.now().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataInicial = LocalDate.parse(dataAtual).minusDays(8).format(formatter);
        String dataFinal = LocalDate.parse(dataAtual).minusDays(1).format(formatter);

        EnviosDAO enviosDao = new EnviosDAO();
        // List<Envio> envios = enviosDao.getEnviosByDate(dataInicial, dataFinal);
        // List<Envio> envios = enviosDao.getEnviosComErroByDate(dataInicial,
        // dataFinal);
        List<Envio> envios = enviosDao.getErrosComDescricaoByDate(dataInicial, dataFinal);

        return envios;
    }
}
