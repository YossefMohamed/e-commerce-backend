package com.ecommerce.fullstack.code.service;

import com.ecommerce.fullstack.code.repository.ProductCategoryRepository;
import com.ecommerce.fullstack.code.repository.ProductRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;



@CrossOrigin("http://localhost:4200")
@Service
public class ReportService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // Report
    public void generateProductsReport(HttpServletResponse response) throws JRException, IOException {
        File file = ResourceUtils.getFile("classpath:test.jrxml");
        JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productRepository.findAll());
        Map<String, Object> parameters = new HashMap<String, Object>();
        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource );
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
        ServletOutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, outputStream);
        outputStream.flush();
        outputStream.close();
    }


    // Report - subReport
    public String generateFullProductsReport()throws JRException, FileNotFoundException {
        // main report path
        File file = ResourceUtils.getFile("classpath:full_product_report.jrxml");
        // sub report path
        File subfile = ResourceUtils.getFile("classpath:ecommerce-sub-report_subreport1.jrxml");

        // compile main report
        JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());

        // compile subreport
        JasperReport subreport = JasperCompileManager.compileReport(subfile.getAbsolutePath());

        // main datasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productCategoryRepository.findAll());

        Long cat_id = 1l;

        // sub datasource
        JRDataSource sub_dataSource = new JRBeanCollectionDataSource(productRepository.findAllByCategoryId(cat_id));

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("pDep", cat_id);
        parameters.put("JasperCustomSubReportLocation", subreport); // subreport
        parameters.put("JasperCustomSubReportDataSource", sub_dataSource);  // subreport datasource

        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

        String file_dest_path = "E:\\iReport-5.6.0\\GeneratedReports\\full-product.pdf";

        JasperExportManager.exportReportToPdfFile(print, file_dest_path);

        return  file_dest_path;
    }


}
