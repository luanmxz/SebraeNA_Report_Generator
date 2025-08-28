package com.luanmarcene.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luanmarcene.dao.EnviosDAO;
import com.luanmarcene.model.Envio;
import com.luanmarcene.model.ErroEeAluno;
import com.luanmarcene.model.ErroEeParticipante;
import com.luanmarcene.model.ErroEeProf;

public class EnviosService {

    public Map<String, List<?>> getEnviosByDate(String dataInicial, String dataFinal)
            throws SQLException, ParseException, IOException, NoSuchFieldException, SecurityException {

        EnviosDAO enviosDao = new EnviosDAO();
        List<Envio> envios = enviosDao.getEnviosByDate(dataInicial, dataFinal);
        List<Envio> erros = enviosDao.getErrosComDescricaoByDate(dataInicial, dataFinal);
        List<ErroEeProf> errosEeProf = enviosDao.getErrosEducacaoEmpreendedoraProf();
        List<ErroEeAluno> errosEeAluno = enviosDao.getErrosEducacaoEmpreendedoraAluno();
        List<ErroEeParticipante> errosEeParticipante = enviosDao.getErrosEducacaoEmpreendedoraParticipante();

        Map<String, List<?>> listasEnviosErros = new HashMap<>();

        listasEnviosErros.put("Envios", envios);
        listasEnviosErros.put("Erros", erros);
        listasEnviosErros.put("Erros EE Prof", errosEeProf);
        listasEnviosErros.put("Erros EE Aluno", errosEeAluno);
        listasEnviosErros.put("Erros EE Participante", errosEeParticipante);

        return listasEnviosErros;
    }
}
