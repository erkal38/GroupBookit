package com.bookit.utilities;

public class ExcelRowCreator {
    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil("src/test/resources/Vytracktestdata.xlsx","QA1-all");
        excelUtil.printCucumberRowIndex();
    }
}
