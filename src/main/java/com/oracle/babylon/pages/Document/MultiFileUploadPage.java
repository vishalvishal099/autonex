package com.oracle.babylon.pages.Document;

import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import org.assertj.core.api.Assert;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains functions to upload files
 */
public class MultiFileUploadPage extends Navigator {

    //Initialization of Web Elements
    private By zipFileUpload = By.xpath("//div[contains(text(),'Zip File Upload')]");
    private By createFileUpload = By.xpath("//div[contains(text(),'Create Upload Profile')]");
    private By profileName = By.xpath("//input[@name='ImportName']");
    private By profileDescription = By.xpath("//input[@name='ImportDescription']");
    private By saveBtn = By.xpath("//div[contains(text(),'Save')]");
    private By backBtn = By.xpath("//button[@id='btnBack']");
    private By selectUploadProfile = By.xpath("//select[@name='documentImportDefaultsId']");
    private By linkViewAndEditProf = By.xpath("//a[text()='View / Edit Profile']");
    //Zipfile upload
    private By chooseZipFileUploadBtn = By.xpath("//input[@type='file']");
    //rename the button to btn
    private By uploadBtn = By.xpath("//button[@id='btnUpload']");
    //MultiFile Upload
    private By chooseMultiFileUpload = By.xpath("//div[@id='uploaderHelperMsg']//div[@title='Choose Files to Upload']");
    private By startUploadBtn = By.xpath("//button[@id='startUpload']");
    private By fileUploadMessage = By.xpath("//ul[@class='message-list success']");
    private By closeBtn = By.xpath("//button[@id='cancel']");

    /**
     * Function create upload profile
     */
    public void createUploadProfile() {

        $(createFileUpload).click();
        Faker faker = new Faker();
        String profileName = faker.app().name();
        $(profileName).sendKeys(profileName);
        $(profileDescription).sendKeys(faker.app().name());
        $(saveBtn).click();
        $(backBtn).click();

    }

    /**
     * Function to View and Edit Profile
     */
    public void viewAndEditUploadedProfile(String profileName) {
        $(selectUploadProfile).selectOption(profileName);
        $(linkViewAndEditProf).click();
        $(saveBtn).click();
        $(backBtn).click();
    }

    /**
     * Function to  Upload Zipfile
     *
     * @param fileName
     * @param fileLocation
     */
    public void zipFileUpload(String fileLocation, String fileName) {
        commonMethods.waitForElement(driver, zipFileUpload);
        $(zipFileUpload).click();
        String path = fileLocation + "/" + fileName;
        $(chooseZipFileUploadBtn).sendKeys(path);
        $(uploadBtn).click();

    }

}
