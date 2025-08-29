package com.luanmarcene.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.luanmarcene.model.Envio;
import com.luanmarcene.model.ErroEeAluno;
import com.luanmarcene.model.ErroEeParticipante;
import com.luanmarcene.model.ErroEeProf;

public class ExcelService {

    static Workbook workbook = null;

    private static Workbook createOrGetWorkbook() {
        if (workbook instanceof Workbook) {
            return workbook;
        }

        workbook = new XSSFWorkbook();
        return workbook;
    }

    private static Workbook getWorkbook() {
        if (workbook instanceof Workbook) {
            workbook = new XSSFWorkbook();
        }

        return workbook;
    }

    private void closeWorkbook() throws IOException {
        Workbook workbook = getWorkbook();
        if (workbook instanceof Workbook) {
            workbook.close();
        }
    }

    public void generateReport(List<Envio> envios, List<Envio> erros, List<ErroEeProf> errosEeProf,
            List<ErroEeAluno> errosEeAlunos, List<ErroEeParticipante> errosEeParticipante, String dataInicial,
            String dataFinal)
            throws NoSuchFieldException, SecurityException, IOException {
        try {
            createEnviosSheet(envios, erros);
            createDescricaoErrosSheet(envios, erros);
            createSheetErrosEeProf(errosEeProf);
            createSheetErrosEeAluno(errosEeAlunos);
            createSheetErrosEeParticipante(errosEeParticipante);
            writeWorkbookFile(dataInicial, dataFinal);
        } catch (Exception e) {
            e.printStackTrace();
            closeWorkbook();
        }

    }

    private void createEnviosSheet(List<Envio> envios, List<Envio> erros)
            throws IOException, NoSuchFieldException, SecurityException {

        Workbook workbook = createOrGetWorkbook();

        Sheet sheetEnvios = workbook.createSheet("Envios");

        Row header = sheetEnvios.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        String fieldDataEnvio = Envio.class.getDeclaredFields()[0].getName();
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(fieldDataEnvio);
        headerCell.setCellStyle(headerStyle);

        String fieldTipoEnvio = Envio.class.getDeclaredFields()[1].getName();
        headerCell = header.createCell(1);
        headerCell.setCellValue(fieldTipoEnvio);
        headerCell.setCellStyle(headerStyle);

        String fieldDescTipoEnvio = Envio.class.getDeclaredFields()[2].getName();
        headerCell = header.createCell(2);
        headerCell.setCellValue(fieldDescTipoEnvio);
        headerCell.setCellStyle(headerStyle);

        String fieldTotal = Envio.class.getDeclaredFields()[3].getName();
        headerCell = header.createCell(3);
        headerCell.setCellValue(fieldTotal);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Com Sucesso");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Com Erro");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("% de Assertividade");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < envios.size(); i++) {
            Envio envio = envios.get(i);
            Row row = sheetEnvios.createRow(i + 1);

            Cell cellDataEnvio = row.createCell(0);
            cellDataEnvio.setCellValue(envio.getDataEnvio());
            cellDataEnvio.setCellStyle(style);

            Cell cellTipoEnvio = row.createCell(1);
            cellTipoEnvio.setCellValue(envio.getCodTipo());
            cellTipoEnvio.setCellStyle(style);

            Cell cellDescricaoTipoEnvio = row.createCell(2);
            cellDescricaoTipoEnvio.setCellValue(envio.getTipoEnvio());
            cellDescricaoTipoEnvio.setCellStyle(style);

            Cell cellTotal = row.createCell(3);
            cellTotal.setCellValue(envio.getTotalEnvios());
            cellTotal.setCellStyle(style);

            Optional<Envio> optEnvioErro = erros.stream().filter(
                    e -> e.getDataEnvio().equals(envio.getDataEnvio()) &&
                            e.getCodTipo().equals(envio.getCodTipo()))
                    .findFirst();

            Integer qtdErros = 0;
            Integer qtdSucesso = envio.getTotalEnvios() - qtdErros;
            Integer percAssertividade = 100;

            if (optEnvioErro.isPresent()) {
                qtdErros = optEnvioErro.get().getTotalEnvios();
                percAssertividade = (qtdSucesso / envio.getTotalEnvios()) * 100;
            }

            Cell cellSucesso = row.createCell(4);
            cellSucesso.setCellValue(qtdSucesso);
            cellSucesso.setCellStyle(style);

            Cell cellErros = row.createCell(5);
            cellErros.setCellValue(qtdErros);
            cellErros.setCellStyle(style);

            Cell cellAssertividade = row.createCell(6);
            cellAssertividade.setCellValue(percAssertividade);
            cellAssertividade.setCellStyle(style);
        }
    }

    private void createDescricaoErrosSheet(List<Envio> envios, List<Envio> erros)
            throws IOException, NoSuchFieldException, SecurityException {

        Workbook workbook = createOrGetWorkbook();

        Sheet sheetDescErros = workbook.createSheet("Descrição dos Erros");

        Row header = sheetDescErros.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < Envio.class.getDeclaredFields().length; i++) {
            String fieldName = Envio.class.getDeclaredFields()[i].getName();
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(fieldName);
            headerCell.setCellStyle(headerStyle);
        }

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < erros.size(); i++) {
            Envio erro = erros.get(i);
            Row row = sheetDescErros.createRow(i + 1);

            Cell cellDataEnvio = row.createCell(0);
            cellDataEnvio.setCellValue(erro.getDataEnvio());
            cellDataEnvio.setCellStyle(style);

            Cell cellTipoEnvio = row.createCell(1);
            cellTipoEnvio.setCellValue(erro.getCodTipo());
            cellTipoEnvio.setCellStyle(style);

            Cell cellDescricaoTipoEnvio = row.createCell(2);
            cellDescricaoTipoEnvio.setCellValue(erro.getTipoEnvio());
            cellDescricaoTipoEnvio.setCellStyle(style);

            Cell cellTotal = row.createCell(3);
            cellTotal.setCellValue(erro.getTotalEnvios());
            cellTotal.setCellStyle(style);

            Cell cellDescricaoErro = row.createCell(5);
            cellDescricaoErro.setCellValue(erro.getDescErro());
            cellDescricaoErro.setCellStyle(style);

        }
    }

    private void createSheetErrosEeProf(List<ErroEeProf> errosEeProf)
            throws IOException, NoSuchFieldException, SecurityException {

        Workbook workbook = createOrGetWorkbook();

        Sheet sheetErrosEeProf = workbook.createSheet("Erros EE PROF");

        Row header = sheetErrosEeProf.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < ErroEeProf.class.getDeclaredFields().length; i++) {
            String fieldName = ErroEeProf.class.getDeclaredFields()[i].getName();
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(fieldName);
            headerCell.setCellStyle(headerStyle);
        }

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < errosEeProf.size(); i++) {
            ErroEeProf erroEeProf = errosEeProf.get(i);
            Row row = sheetErrosEeProf.createRow(i + 1);

            Cell cellTipo = row.createCell(0);
            cellTipo.setCellValue(erroEeProf.getTipo());
            cellTipo.setCellStyle(style);

            Cell cellCodErro = row.createCell(1);
            cellCodErro.setCellValue(erroEeProf.getCodErro());
            cellCodErro.setCellStyle(style);

            Cell cellDescricaoErro = row.createCell(2);
            cellDescricaoErro.setCellValue(erroEeProf.getDescricaoErro());
            cellDescricaoErro.setCellStyle(style);

            Cell cellCodEventoPR = row.createCell(3);
            cellCodEventoPR.setCellValue(erroEeProf.getCodEventoPR());
            cellCodEventoPR.setCellStyle(style);

            Cell cellCodEventoNA = row.createCell(4);
            cellCodEventoNA.setCellValue(erroEeProf.getCodEventoNA());
            cellCodEventoNA.setCellStyle(style);

            Cell cellNomeResponsavel = row.createCell(5);
            cellNomeResponsavel.setCellValue(erroEeProf.getNomeResponsavel());
            cellNomeResponsavel.setCellStyle(style);

            Cell cellCpfCnpjResponsavel = row.createCell(6);
            cellCpfCnpjResponsavel.setCellValue(erroEeProf.getCpfCnpjResponsavel());
            cellCpfCnpjResponsavel.setCellStyle(style);

            Cell cellCodProdutoNA = row.createCell(7);
            cellCodProdutoNA.setCellValue(erroEeProf.getCodProdutoNA());
            cellCodProdutoNA.setCellStyle(style);

            Cell cellCodProdutoPR = row.createCell(8);
            cellCodProdutoPR.setCellValue(erroEeProf.getCodProdutoPR());
            cellCodProdutoPR.setCellStyle(style);

            Cell cellQtdMaxParticipantes = row.createCell(9);
            cellQtdMaxParticipantes.setCellValue(erroEeProf.getQtdMaxParticipantes());
            cellQtdMaxParticipantes.setCellStyle(style);

            Cell cellQtdParticipantesAtivos = row.createCell(10);
            cellQtdParticipantesAtivos.setCellValue(erroEeProf.getQtdParticipantesAtivos());
            cellQtdParticipantesAtivos.setCellStyle(style);

            Cell cellQtdDisponivel = row.createCell(11);
            cellQtdDisponivel.setCellValue(erroEeProf.getQtdDisponivel());
            cellQtdDisponivel.setCellStyle(style);

            Cell cellCodRealizacaoNA = row.createCell(12);
            cellCodRealizacaoNA.setCellValue(erroEeProf.getCodRealizacaoNA());
            cellCodRealizacaoNA.setCellStyle(style);
        }
    }

    private void createSheetErrosEeAluno(List<ErroEeAluno> errosEeAlunos) {
        Workbook workbook = createOrGetWorkbook();

        Sheet sheetErrosEeAluno = workbook.createSheet("Erros EE Aluno");

        Row header = sheetErrosEeAluno.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < ErroEeAluno.class.getDeclaredFields().length; i++) {
            String fieldName = ErroEeAluno.class.getDeclaredFields()[i].getName();
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(fieldName);
            headerCell.setCellStyle(headerStyle);
        }

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < errosEeAlunos.size(); i++) {
            ErroEeAluno erroEeAluno = errosEeAlunos.get(i);
            Row row = sheetErrosEeAluno.createRow(i + 1);

            Cell cellTipo = row.createCell(0);
            cellTipo.setCellValue(erroEeAluno.getTipo());
            cellTipo.setCellStyle(style);

            Cell cellCodErro = row.createCell(1);
            cellCodErro.setCellValue(erroEeAluno.getCodErro());
            cellCodErro.setCellStyle(style);

            Cell cellDescErro = row.createCell(2);
            cellDescErro.setCellValue(erroEeAluno.getDescricaoErro());
            cellDescErro.setCellStyle(style);

            Cell cellCodEventoPR = row.createCell(3);
            cellCodEventoPR.setCellValue(erroEeAluno.getCodEventoPR());
            cellCodEventoPR.setCellStyle(style);

            Cell cellCodEventoNA = row.createCell(4);
            cellCodEventoNA.setCellValue(erroEeAluno.getCodEventoNA());
            cellCodEventoNA.setCellStyle(style);

            Cell cellNomeResponsavel = row.createCell(5);
            cellNomeResponsavel.setCellValue(erroEeAluno.getNomeResponsavel());
            cellNomeResponsavel.setCellStyle(style);

            Cell cellCpfCnpjResponsavel = row.createCell(6);
            cellCpfCnpjResponsavel.setCellValue(erroEeAluno.getCpfCnpjResponsavel());
            cellCpfCnpjResponsavel.setCellStyle(style);

            Cell cellCodProdutoNA = row.createCell(7);
            cellCodProdutoNA.setCellValue(erroEeAluno.getCodProdutoNA());
            cellCodProdutoNA.setCellStyle(style);

            Cell cellCodProdutoPR = row.createCell(8);
            cellCodProdutoPR.setCellValue(erroEeAluno.getCodProdutoPR());
            cellCodProdutoPR.setCellStyle(style);
        }
    }

    private void createSheetErrosEeParticipante(List<ErroEeParticipante> errosEeParticipante) {
        Workbook workbook = createOrGetWorkbook();

        Sheet sheetErrosEeParticipante = workbook.createSheet("Erros EE Participantes");

        Row header = sheetErrosEeParticipante.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < ErroEeParticipante.class.getDeclaredFields().length; i++) {
            String fieldName = ErroEeParticipante.class.getDeclaredFields()[i].getName();
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(fieldName);
            headerCell.setCellStyle(headerStyle);
        }

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < errosEeParticipante.size(); i++) {
            ErroEeParticipante erroEeParticipante = errosEeParticipante.get(i);
            Row row = sheetErrosEeParticipante.createRow(i + 1);

            Cell cellTipo = row.createCell(0);
            cellTipo.setCellValue(erroEeParticipante.getTipo());
            cellTipo.setCellStyle(style);

            Cell cellCpfCnpjParticipante = row.createCell(1);
            cellCpfCnpjParticipante.setCellValue(erroEeParticipante.getCpfCnpjParticipante());
            cellCpfCnpjParticipante.setCellStyle(style);

            Cell cellNomeParticipante = row.createCell(2);
            cellNomeParticipante.setCellValue(erroEeParticipante.getNomeParticipante());
            cellNomeParticipante.setCellStyle(style);

            Cell cellCodErro = row.createCell(3);
            cellCodErro.setCellValue(erroEeParticipante.getCodErro());
            cellCodErro.setCellStyle(style);

            Cell cellDescErro = row.createCell(4);
            cellDescErro.setCellValue(erroEeParticipante.getDescricaoErro());
            cellDescErro.setCellStyle(style);

            Cell cellCodEventoNA = row.createCell(5);
            cellCodEventoNA.setCellValue(erroEeParticipante.getCodEventoNA());
            cellCodEventoNA.setCellStyle(style);

            Cell cellCodEventoPR = row.createCell(6);
            cellCodEventoPR.setCellValue(erroEeParticipante.getCodEventoPR());
            cellCodEventoPR.setCellStyle(style);

            Cell cellNomeResponsavel = row.createCell(7);
            cellNomeResponsavel.setCellValue(erroEeParticipante.getNomeResponsavel());
            cellNomeResponsavel.setCellStyle(style);

            Cell cellCpfCnpjResponsvel = row.createCell(8);
            cellCpfCnpjResponsvel.setCellValue(erroEeParticipante.getCpfCnpjResponsavel());
            cellCpfCnpjResponsvel.setCellStyle(style);

            Cell cellCodProdutoNA = row.createCell(9);
            cellCodProdutoNA.setCellValue(erroEeParticipante.getCodProdutoNA());
            cellCodProdutoNA.setCellStyle(style);

            Cell cellCodProdutoPR = row.createCell(10);
            cellCodProdutoPR.setCellValue(erroEeParticipante.getCodProdutoPR());
            cellCodProdutoPR.setCellStyle(style);
        }
    }

    private void writeWorkbookFile(String dataInicial, String dataFinal) throws IOException {
        Workbook workbook = createOrGetWorkbook();

        File currDir = new File(".");
        String absPath = currDir.getAbsolutePath();
        String path = absPath.substring(0, absPath.length() - 1).concat("report-generator\\out");

        String fileName = String.format("Diario de Bordo - EnviosSAS-SebraeNA de %s ate %s.xlsx", dataInicial,
                dataFinal);

        String fileLocation = path + "\\" + fileName;

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        closeWorkbook();
    }
}
