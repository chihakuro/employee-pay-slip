package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Any setup before tests (if needed)
    }

    @Test
    public void testShowForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void testUploadCSV() throws Exception {
        // Prepare the input CSV file
        String csvContent = """
                            FirstName,LastName,AnnualSalary,SuperRate,DateRange
                            Monica,Tan,60050,9.0,01 March \u2013 31 March
                            Brend,Tulu,120000,50.0,01 March \u2013 31 March""";

        MockMultipartFile file = new MockMultipartFile("file", "input.csv", "text/csv", csvContent.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/generate")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Disposition", "attachment; filename=\"payslip.csv\""))
                .andExpect(MockMvcResultMatchers.content().contentType("text/csv"));
    }
}
