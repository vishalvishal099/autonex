package com.oracle.babylon.pages.Document;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class MultipleFileUpload extends Navigator {

    private By multiFileUploadBtn = By.xpath("//button[@title='Multi File Upload']");
    private By fileUploadInput = By.xpath("//input[@title='file input']");
    private By startUploadBtn = By.xpath("//button[@id='startUpload']");
    private By uploadSuccessMessage = By.xpath("//div[@id='export-success-panel']");


    public void clickMultiFileUploadBtn(String directoryPath) {
        List<String> filesToUpload = returnFileNames(directoryPath);
        String fileString = "";
        driver.switchTo().frame("frameMain");
        $(multiFileUploadBtn).click();
        for (String file : filesToUpload) {
            fileString += file + " \n ";

        }
        fileString = fileString.substring(0, fileString.length() - 3);
        $(fileUploadInput).sendKeys(fileString);
        $(startUploadBtn).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(uploadSuccessMessage));
    }

    public List<String> returnFileNames(String directoryPath) {
        List<String> results = new ArrayList<>();


        File[] files = new File(directoryPath).listFiles();
//If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.toString());
            }
        }
        return results;
    }

    public ZonedDateTime convertTimeOzToUK() {

        Instant nowUtc = Instant.now();
        ZoneId australiaSydney = ZoneId.of("Australia/Sydney");
        ZonedDateTime nowAusSydney = ZonedDateTime.ofInstant(nowUtc, australiaSydney);
        System.out.println("Current date");
        System.out.println(nowAusSydney);
        return nowAusSydney;


    }


    public void returnRequiredDate(String requirement) {
        ZonedDateTime currentDate = convertTimeOzToUK();
        System.out.println("Changed date");
        switch (requirement) {
            case "yesterday":
                ZonedDateTime yesterday = currentDate.minusDays(1);
                System.out.println(yesterday);
                break;
            case "today":
                System.out.println(currentDate);
                break;
            case "tomorrow":
                ZonedDateTime tomorrow = currentDate.plusDays(1);
                System.out.println(tomorrow);
                break;
        }
    }

}
