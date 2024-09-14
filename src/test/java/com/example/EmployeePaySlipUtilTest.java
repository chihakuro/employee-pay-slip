package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class EmployeePaySlipUtilTest {

    // Test the readCSV method
    @Test
    public void testReadCSV() throws IOException {
        // Prepare a mock CSV file
        String csvContent = """
                            FirstName,LastName,AnnualSalary,SuperRate,PaymentStartDate
                            John,Doe,60000,10,01 March \u2013 31 March
                            Jane,Smith,120000,9,01 March \u2013 31 March
                            """;

        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "employees.csv", 
                "text/csv", 
                csvContent.getBytes()
        );

        // Call the readCSV method
        List<String[]> result = EmployeePaySlipUtil.readCSV(file);

        // Assertions
        assertEquals(2, result.size());  // 2 rows of data (excluding header)

        // First employee's data
        assertArrayEquals(new String[]{"John", "Doe", "60000", "10", "01 March – 31 March"}, result.get(0));
        
        // Second employee's data
        assertArrayEquals(new String[]{"Jane", "Smith", "120000", "9", "01 March – 31 March"}, result.get(1));
    }

    // Test readCSV with an empty file
    @Test
    public void testReadCSV_EmptyFile() throws IOException {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.csv", "text/csv", "".getBytes());

        List<String[]> result = EmployeePaySlipUtil.readCSV(emptyFile);

        // Assertions
        assertTrue(result.isEmpty());  // No data should be returned
    }

    // Test the convertToCSV method
    @Test
    public void testConvertToCSV() {
        // Prepare some data
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"John Doe", "01 March – 31 March", "5000", "921", "4079", "500"});
        data.add(new String[]{"Jane Smith", "01 March – 31 March", "10000", "2696", "7304", "900"});

        // Call the convertToCSV method
        String csvOutput = EmployeePaySlipUtil.convertToCSV(data);

        // Expected CSV format
        String expectedCsv = """
                             Name,Pay Period,Gross Income,Income Tax,Net Income,Super
                             John Doe,01 March \u2013 31 March,5000,921,4079,500
                             Jane Smith,01 March \u2013 31 March,10000,2696,7304,900
                             """;

        // Assertions
        assertEquals(expectedCsv, csvOutput);
    }

    // Test convertToCSV with an empty list
    @Test
    public void testConvertToCSV_EmptyData() {
        // Call convertToCSV with an empty list
        String csvOutput = EmployeePaySlipUtil.convertToCSV(new ArrayList<>());

        // Expected output should only contain the header
        String expectedCsv = "Name,Pay Period,Gross Income,Income Tax,Net Income,Super\n";

        // Assertions
        assertEquals(expectedCsv, csvOutput);
    }
}
