package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EmployeePaySlipTest {

    // Test the generatePaySlip method
    @Test
    public void testGeneratePaySlip() {
        EmployeePaySlip paySlip = new EmployeePaySlip();

        // Test data
        String firstName = "John";
        String lastName = "Doe";
        double annualSalary = 60000;
        double superRate = 10;
        String paymentStartDate = "01 March – 31 March";

        // Call the method
        String[] result = paySlip.generatePaySlip(firstName, lastName, annualSalary, superRate, paymentStartDate);

        // Assertions
        assertEquals("John Doe", result[0]);  // Name
        assertEquals("01 March – 31 March", result[1]);  // Pay Period
        assertEquals("5000", result[2]);  // Gross Income (60000 / 12)
        assertEquals("921", result[3]);  // Income Tax (based on tax brackets)
        assertEquals("4079", result[4]);  // Net Income (Gross Income - Income Tax)
        assertEquals("500", result[5]);  // Super (Gross Income * 10%)
    }

    // Test the calculateIncomeTax method
    @Test
    public void testCalculateIncomeTax() {
        EmployeePaySlip paySlip = new EmployeePaySlip();

        // Test tax calculation for different salary ranges
        assertEquals(0, paySlip.calculateIncomeTax(18000));  // No tax for salary below 18,200
        assertEquals(342, paySlip.calculateIncomeTax(20000));  // Salary within 18,201–37,000
        assertEquals(3572 + 0.325 * (60000 - 37000), paySlip.calculateIncomeTax(60000));  // Salary within 37,001–87,000
        assertEquals(19822 + 0.37 * (150000 - 87000), paySlip.calculateIncomeTax(150000));  // Salary within 87,001–180,000
        assertEquals(54232 + 0.45 * (200000 - 180000), paySlip.calculateIncomeTax(200000));  // Salary above 180,000
    }
}
