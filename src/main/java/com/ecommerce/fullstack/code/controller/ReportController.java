package com.ecommerce.fullstack.code.controller;

import com.ecommerce.fullstack.code.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    private ReportService service;

    @GetMapping(value = "/report")
    public void viewReport(HttpServletResponse response) throws JRException, IOException {
        service.generateProductsReport(response);
    }

}
