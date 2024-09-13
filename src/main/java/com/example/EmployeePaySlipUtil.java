package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EmployeePaySlipUtil {

    // Method to read CSV data from an uploaded file
    public static List<String[]> readCSV(MultipartFile file) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header
                    continue;
                }
                String[] values = line.split(",");
                data.add(values);
            }
        }
        return data;
    }

    // Method to convert List<String[]> to a CSV format string
    public static String convertToCSV(List<String[]> data) {
        StringBuilder sb = new StringBuilder();
        
        // Add the header
        sb.append("Name,Pay Period,Gross Income,Income Tax,Net Income,Super\n");

        // Add the data rows
        for (String[] line : data) {
            sb.append(String.join(",", line)).append("\n");
        }
        
        return sb.toString();
    }
}
