package com.example.Bach.controller;

import com.example.Bach.model.List;
import com.example.Bach.service.ListService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ExcelExport {

    private final ListService listingService;

    public ExcelExport(ListService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        java.util.List<List> listings = listingService.getAllListings();

        // Створення робочої книги
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Оголошення");

        // Створення заголовків
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Заголовок", "Ціна", "Валюта", "Посилання"};

        // Стиль для заголовків
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Створення заголовків з стилем
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Заповнення даними
        int rowNum = 1;
        for (List listing : listings) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(listing.getTitle());
            row.createCell(1).setCellValue(listing.getPrice().doubleValue());
            row.createCell(2).setCellValue(listing.getCurrency());
            row.createCell(3).setCellValue(listing.getUrl());
        }

        // Авторозмір колонок
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Встановлення заголовків відповіді
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=listings.xlsx");

        // Запис у відповідь
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}