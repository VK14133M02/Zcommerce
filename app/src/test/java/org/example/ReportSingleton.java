package org.example;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import com.relevantcodes.extentreports.ExtentReports;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ReportSingleton {

    private static ReportSingleton instanceOfSingletonReport = null;
    
    private ExtentReports report;


    public static String getTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp).replace(':', '-').replace(' ', '_').replace('.', '-');
    }
    
    public static String captureScreenShot(WebDriver driver) throws IOException{
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/Screenshots/";
        File destFile = new File(screenshotPath + getTimeStamp() + ".png");
        // Ensure the screenshots directory exists
        File screenshotDir = new File("/Screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        String errflpath = destFile.getAbsolutePath();
        FileUtils.copyFile(srcFile,destFile);
        return errflpath;
    } 

    private ReportSingleton() {
        String reportPath = System.getProperty("user.dir") + "/Reports/";
        File reportDir = new File("/Reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        report = new ExtentReports(reportPath + "ExtentReportResults_" + getTimeStamp() + ".html");
        report.loadConfig(new File(System.getProperty("user.dir") + "/extent_customization_configs.xml"));
    }

    public static ReportSingleton getInstanceOfSingletonReport() {
        if (instanceOfSingletonReport == null) {
            instanceOfSingletonReport = new ReportSingleton();
        }
        return instanceOfSingletonReport;
    }

    public ExtentReports getReport(){
        return report;
    }

}


// public static String captureScreenShot(WebDriver driver, String screenshotType, String description) {
//     try {
//         File theDir = new File("/Screenshots");
//         if (!theDir.exists()) {
//             theDir.mkdirs();
//         }
//         String timestamp = String.valueOf(java.time.LocalDateTime.now()).replace(':', '-');
//         String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);
//         TakesScreenshot scrShot = ((TakesScreenshot) driver);
//         File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//         File DestFile = new File("screenshots/" + fileName);
//         FileUtils.copyFile(SrcFile, DestFile);
//         return DestFile.getAbsolutePath();
//     } catch (Exception e) {
//         e.printStackTrace();
//         return "";
//     }
// }