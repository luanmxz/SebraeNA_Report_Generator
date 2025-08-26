package com.luanmarcene;

import java.sql.SQLException;
import java.text.ParseException;

import com.luanmarcene.service.EnviosService;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {

        EnviosService enviosService = new EnviosService();
        enviosService.getEnviosByDate();
    }
}
