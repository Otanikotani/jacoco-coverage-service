package com.aurea.jacoco;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests {

    private static final Path SAMPLES;

    static {
        URL url = FileUploadTests.class.getResource("");
        try {
            SAMPLES = Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to find ../report folder");
        }
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSuccessfullyReturnConverted() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "jacoco.xml", "text/xml",
                        new FileInputStream(SAMPLES.resolve("jacoco.xml").toFile()));
        this.mvc.perform(fileUpload("/").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.moduleCoverage.name", is("Eloc")))
                .andExpect(jsonPath("$.moduleCoverage.packageCoverages[0].classCoverages[0].methodCoverages[0].name",
                        is("Duplicate(int, Range, int, String)")));
    }

    @Test
    public void shouldReturnBadRequestWithCauseOnBadFile() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "not-a-jacoco.xml", "text/plain", "hello world".getBytes());
        this.mvc.perform(fileUpload("/").file(multipartFile))
                .andExpect(status().isBadRequest());
    }
}