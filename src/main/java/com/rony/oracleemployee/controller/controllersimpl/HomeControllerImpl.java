package com.rony.oracleemployee.controller.controllersimpl;

import com.rony.oracleemployee.controller.HomeController;
import com.rony.oracleemployee.dtos.request.UserInfoDto;
import com.rony.oracleemployee.dtos.request.UserLoginDto;
import com.rony.oracleemployee.model.Employee;
import com.rony.oracleemployee.model.User;
import com.rony.oracleemployee.services.UserService;
import com.rony.oracleemployee.utils.ExcelFileExporter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class HomeControllerImpl implements HomeController {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserInfoDto> login(UserLoginDto userLoginDto) {
        System.out.println("username "+userLoginDto.getUsername());
        System.out.println("password "+userLoginDto.getPassword());

        return ResponseEntity.ok(userService.loginUser(userLoginDto));
    }

    @Override
    public ResponseEntity<Void> register(UserInfoDto userInfoDto) {
        userService.addUser(userInfoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/demo")
    ResponseEntity<Void> demoPage(){
        return ResponseEntity.ok().build();
    }

    // Method to create the pdf report via jasper framework.
    @GetMapping(value = "/download/users/pdf")
    public ResponseEntity<Void> viewReport() {
        log.info("Preparing the pdf report via jasper.");
        try {
            createPdfReport(userService.getAllUsers());
            log.info("File successfully saved at the given path.");
        } catch (final Exception e) {
            log.error("Some error has occurred while preparing the employee pdf report.");
            e.printStackTrace();
        }
        // Returning the view name as the index page for ease.
//        model.setViewName("welcome");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/users/excel")
    public void downloadUsersExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;  filename=user.xlsx");

        //################## excel two ######################
        ByteArrayInputStream inputStream = ExcelFileExporter.exportUserListToExcelFile(userService.getAllUsers());
        IOUtils.copy(inputStream,response.getOutputStream());
    }

    // Method to create the pdf file using the employee list datasource.
    private void createPdfReport(final List<User> users) throws JRException {
        // Fetching the .jrxml file from the resources folder.
        final InputStream stream = this.getClass().getResourceAsStream("/jasper.jrxml");

        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);

        // Fetching the users from the data source.
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(users);

        // Adding the additional parameters to the pdf.
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "rony.com");

        // Filling the report with the employee data and additional parameters information.
        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

        // Users can change as per their project requrirements or can take it as request input requirement.
        // For simplicity, this tutorial will automatically place the file under the "c:" drive.
        // If users want to download the pdf file on the browser, then they need to use the "Content-Disposition" technique.
//        final String filePath = "\\";
        final String filePath = System.getProperty("user.home") + "\\teletalk-demo\\temp\\";
        // Export the report to a PDF file.
        JasperExportManager.exportReportToPdfFile(print, filePath + "user_report.pdf");

        String sourceFileName = "D:\\Intern\\OracleEmployee\\src\\main\\resources\\excel.jrxml";
//        String printFileName = null;
//
//        JRBeanCollectionDataSource beanColDataSource =
//                new JRBeanCollectionDataSource(users);
//
//        Map nParameters = new HashMap();
//        try {
//            printFileName = JasperFillManager.fillReportToFile(sourceFileName,
//                    nParameters, beanColDataSource);
//            if (printFileName != null) {
//                /**
//                 * 1- export to PDF
//                 */
//                JasperExportManager.exportReportToPdfFile(printFileName,
//                        "C://sample_report.pdf");
//
//                /**
//                 * 2- export to HTML
//                 */
//                JasperExportManager.exportReportToHtmlFile(printFileName,
//                        "C://sample_report.html");
//
//                /**
//                 * 3- export to Excel sheet
//                 */
//                JRXlsExporter exporter = new JRXlsExporter();
//
//                exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
//                        printFileName);
//                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
//                        "C://sample_report.xls");
//
//                exporter.exportReport();
//            }
//        } catch (JRException e) {
//            e.printStackTrace();
//        }

//        ################# excel one ####################
//        final InputStream stream2 = this.getClass().getResourceAsStream("/excel.jrxml");
//
//        // Compile the Jasper report from .jrxml to .japser
//        final JasperReport jasperReport = JasperCompileManager.compileReport(stream2);
//        Map<String, Object> params = new HashMap<>();
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,source);
//
//        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//        configuration.setOnePagePerSheet(true);
//        configuration.setIgnoreGraphics(false);
//
//        File outputFile = new File(filePath+"output.xlsx");
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//             OutputStream fileOutputStream = new FileOutputStream(outputFile)) {
//            Exporter exporter = new JRXlsxExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
//            exporter.setConfiguration(configuration);
//            exporter.exportReport();
//            byteArrayOutputStream.writeTo(fileOutputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
