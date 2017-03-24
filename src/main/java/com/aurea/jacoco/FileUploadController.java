package com.aurea.jacoco;

import com.aurea.jacoco.parser.JacocoParserException;
import com.aurea.jacoco.parser.JacocoParsers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileUploadController {

    @RequestMapping("/")
    public JacocoIndex parseJacocoXml(@RequestParam("file") MultipartFile file) throws IOException {
        return JacocoParsers.fromXml(file.getInputStream());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IOException.class, JacocoParserException.class})
    public void parseError(HttpServletRequest req, Exception ex) {
        new ErrorInfo(req.getRequestURI(), ex);
    }
}