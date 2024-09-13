package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeePaySlip {

    public static void main(String[] args) {
        SpringApplication.run(EmployeePaySlip.class, args);
    }

    // Method to generate payslip for each employee
    public String[] generatePaySlip(String firstName, String lastName, double annualSalary, double superRate, String paymentStartDate) {
        int grossIncome = (int) Math.round(annualSalary / 12);
        int incomeTax = (int) Math.round(calculateIncomeTax(annualSalary) / 12);
        int netIncome = grossIncome - incomeTax;
        int superAmount = (int) Math.round(grossIncome * superRate / 100);

        return new String[]{firstName + " " + lastName, paymentStartDate, String.valueOf(grossIncome), String.valueOf(incomeTax), String.valueOf(netIncome), String.valueOf(superAmount)};
    }

    // Method to calculate income tax based on salary
    public double calculateIncomeTax(double annualSalary) {
        double tax;
        if (annualSalary <= 18200) {
            tax = 0;
        } else if (annualSalary <= 37000) {
            tax = (annualSalary - 18200) * 0.19;
        } else if (annualSalary <= 87000) {
            tax = 3572 + (annualSalary - 37000) * 0.325;
        } else if (annualSalary <= 180000) {
            tax = 19822 + (annualSalary - 87000) * 0.37;
        } else {
            tax = 54232 + (annualSalary - 180000) * 0.45;
        }
        return tax;
    }
}
