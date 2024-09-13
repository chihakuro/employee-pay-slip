package com.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@Controller
public class HomeController {

    private final EmployeePaySlip paySlipGenerator = new EmployeePaySlip();

    // Method to serve the HTML form when navigating to the root URL
    @GetMapping("/")
    public String showForm(Model model) {
        return "form";  // "form" corresponds to form.html (without .html extension)
    }
    
    @PostMapping("/generate")
    public ResponseEntity<byte[]> uploadCSV(@RequestParam("file") MultipartFile file) {
        List<String[]> employeeData;
        try {
            employeeData = EmployeePaySlipUtil.readCSV(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error reading the CSV file: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    
        List<String[]> outputData = new ArrayList<>();
        try {
            for (String[] data : employeeData) {
                String firstName = data[0];
                String lastName = data[1];
                double annualSalary = Double.parseDouble(data[2]);
                double superRate = Double.parseDouble(data[3]);
                String paymentStartDate = data[4];
    
                String[] output = paySlipGenerator.generatePaySlip(firstName, lastName, annualSalary, superRate, paymentStartDate);
                outputData.add(output);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating payslip: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    
        // Convert the outputData to CSV format
        String csvOutput = EmployeePaySlipUtil.convertToCSV(outputData);
    
        // Set response headers to allow CSV download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"payslip.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
    
        return new ResponseEntity<>(csvOutput.getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
    }
}
