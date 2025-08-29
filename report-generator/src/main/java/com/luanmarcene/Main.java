package com.luanmarcene;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


import com.luanmarcene.model.Envio;
import com.luanmarcene.model.ErroEeAluno;
import com.luanmarcene.model.ErroEeParticipante;
import com.luanmarcene.model.ErroEeProf;
import com.luanmarcene.service.EmailService;
import com.luanmarcene.service.EnviosService;
import com.luanmarcene.service.ExcelService;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
            throws SQLException, ParseException, IOException, NoSuchFieldException, SecurityException {

        String dataAtual = LocalDate.now().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataInicial = LocalDate.parse(dataAtual).minusDays(8).format(formatter);
        String dataFinal = LocalDate.parse(dataAtual).minusDays(1).format(formatter);

        EnviosService enviosService = new EnviosService();
        Map<String, List<?>> listasEnviosErros = enviosService.getEnviosByDate(dataInicial, dataFinal);

        ExcelService excelService = new ExcelService();
        excelService.generateReport(
                (List<Envio>) listasEnviosErros.get("Envios"),
                (List<Envio>) listasEnviosErros.get("Erros"),
                (List<ErroEeProf>) listasEnviosErros.get("Erros EE Prof"),
                (List<ErroEeAluno>) listasEnviosErros.get("Erros EE Aluno"),
                (List<ErroEeParticipante>) listasEnviosErros.get("Erros EE Participante"),
                dataInicial, dataFinal);

        EmailService emailService = new EmailService();
        emailService.sendReportEmail();
    }
}
