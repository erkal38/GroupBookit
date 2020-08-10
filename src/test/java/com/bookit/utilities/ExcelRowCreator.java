package com.bookit.utilities;

public class ExcelRowCreator {
    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil("src/test/resources/BookIt.xlsx","Sheet1");
        excelUtil.printCucumberRowIndex();
    }
}
