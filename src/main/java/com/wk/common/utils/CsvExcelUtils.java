package com.wk.common.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.List;

public class CsvExcelUtils {

    public static String exportCsv(String name , String [] title , List<List<String>> object) {
        BufferedWriter bufferedWriter = null;
        OutputStream outputStream = null;
        try {
            String fileChName = "/home/www/export/" + name + ".csv" ;
            outputStream = new FileOutputStream(fileChName);
            byte[] bytes = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
            outputStream.write(bytes);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream , "UTF-8"));
            CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(title);
            CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, csvFormat);
            csvPrinter.printRecords(object);
            return fileChName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
