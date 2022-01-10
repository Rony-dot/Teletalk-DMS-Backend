package com.rony.oracleemployee.controller.controllersimpl;


import com.rony.oracleemployee.controller.UserController;
import com.rony.oracleemployee.dtos.request.UserInfoDto;
import com.rony.oracleemployee.model.User;
import com.rony.oracleemployee.services.UserService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> add( UserInfoDto userInfoDto) {
        userService.addUser(userInfoDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<User> getById(long id) {
        var user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
       return  ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(User user, long id) {
        return null;
    }

    @GetMapping("/download/pdf")
    public void getDocumentPdf(HttpServletResponse response) throws IOException, JRException {
        // Fetching the .jrxml file from the resources folder.
        final InputStream stream = this.getClass().getResourceAsStream("/jasper.jrxml");

        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);

        // Fetching the users from the data source.
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(userService.getAllUsers());

        // Adding the additional parameters to the pdf.
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "rony.com");

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=users.pdf;");

        // Filling the report with the employee data and additional parameters information.
        final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    }

    @GetMapping("/download/excel")
    public void getDocumentExcel(HttpServletResponse response) throws IOException, JRException {
        // Fetching the .jrxml file from the resources folder.
        final InputStream stream = this.getClass().getResourceAsStream("/excelGit.jrxml");

        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(userService.getAllUsers());
        Map<String, Object> parameters = new HashMap();
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, beanColDataSource);
        JRXlsxExporter exporter = new JRXlsxExporter();
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setSheetNames(new String[] { "sheet1" });
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true); // Detect cell types (date and etc.)
        configuration.setWhitePageBackground(false); // No white background!
        configuration.setFontSizeFixEnabled(false);

        // No spaces between rows and columns
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);

        response.setHeader("Content-Disposition", "attachment;filename=users.xlsx");
        response.setContentType("application/octet-stream");
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();

    }
}
