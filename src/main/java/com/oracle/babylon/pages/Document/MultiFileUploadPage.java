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
    //Can we only put the text() as part of the identifier and use contains rather than = operator
    private By multiFileUpload= By.xpath("//div[@class='uiButton-label' and text()='Multi File Upload']");
    private By zipFileUpload=By.xpath("//div[@class='uiButton-label' and text()='Zip File Upload']");
    private By createFileUpload=By.xpath("//div[@class='uiButton-label' and text()='Create Upload Profile']");
    private By profileName=By.xpath("//input[@name='ImportName']");
    private By profileDescription=By.xpath("//input[@name='ImportDescription']");
    private By saveButton=By.xpath("//div[@class='uiButton-label' and text()='Save']");
    private By backButton=By.xpath("//button[@id='btnBack']");
    private By selectUploadProfile=By.xpath("//select[@name='documentImportDefaultsId']");
    private By linkViewAndEditProf=By.xpath("//a[text()='View / Edit Profile']");
    //Zipfile upload
    //Please rename to chooseZipFileUploadBtn
    private By ChooseZipFileUpload=By.xpath("//input[@type='file']");
    //rename the button to btn
    private By uploadButton=By.xpath("//button[@id='btnUpload']");
    //MultiFile Upload
    private By chooseMultiFileUpload=By.xpath("//div[@id='uploaderHelperMsg']//div[@title='Choose Files to Upload']");
    private By startUploadButton= By.xpath("//button[@id='startUpload']");
    private By fileUploadMessage=By.xpath("//ul[@class='message-list success']");
    private By closeButton=By.xpath("//button[@id='cancel']");

    /**
     * Function create upload profile
     */
    public void createUploadProfile() {

        $(createFileUpload).click();
        Faker faker=new Faker();
        //Store the faker.app().name() in a variable and use it.
        $(profileName).sendKeys(faker.app().name());
        $(profileDescription).sendKeys(faker.app().name());
        $(saveButton).click();
        $(backButton).click();

    }
    /**
     * Function to View and Edit Profile
     */
    public void viewAndEditUploadedProfile(String profileName)
    {
        $(selectUploadProfile).selectOption(profileName);
        $(linkViewAndEditProf).click();
        $(saveButton).click();
        $(backButton).click();
    }

    /**
     * Function to  Upload Zipfile
     * @param fileName
     * @param fileLocation
     */
    public void zipFileUpload(String fileLocation,String fileName)
    {
        //Do we need a try catch block. It will not fail the test case if not handled properly.
        try
        {
            //Remove the navigation method calls
            getMenuSubmenu("Documents", "Multiple File Upload");
            this.driver = commonMethods.switchToFrame(driver, "frameMain");
            commonMethods.waitForElement(driver,zipFileUpload);
            $(zipFileUpload).click();
            //Didnt understand the code in send keys. Please be a bit more clear 
            $(ChooseZipFileUpload).sendKeys("+fileLocation+"+"+fileName+");
            $(uploadButton).click();
            //Use waitExplicitly method
            commonMethods.wait(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

}
