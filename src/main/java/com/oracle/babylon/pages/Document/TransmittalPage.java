package com.oracle.babylon.pages.Document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Directory.DirectoryPage;
import com.oracle.babylon.pages.Mail.MailPage;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains method to enable a user to create a transmittal
 */
public class TransmittalPage extends Navigator {

    //Initialization of Web Elements
    private By toIdTextBox = By.xpath("//input[@name='SPEED_ADDRESS_TO']");
    private By subject = By.id("Correspondence_subject");
    private By sendBtn = By.xpath("//button[@id='btnSend']");
    private By directoryBtn = By.xpath("//div[text()='Directory']");
    private By reasonForIssueSelectBox = By.xpath("//select[@id='Correspondence_correspondenceReasonID']");

    private DirectoryPage directoryPage = new DirectoryPage();


    /**
     *  Create a transmittal with minimal data. To id, subject and attribute is provided.
     */
    public void createBasicTransmittal(DataTable dataTable) {
        driver = WebDriverRunner.getWebDriver();
        Map<String, String> featureDataMap = dataTable.transpose().asMap(String.class, String.class);
        //The data is taken from userData.json file and we search for the project in admin tool
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.getUserDataJsonFilePath());
        //Project info
        Map<String, String> mailAttributeMap = mapOfMap.get("mailattribute");
        commonMethods.waitForElement(driver, directoryBtn);
        $(directoryBtn).click();
        commonMethods.clickListToChange(By.xpath("//h1[text()='Search - Directory']"), "Global");
        directoryPage.addRecipient(featureDataMap);
        $(subject).sendKeys(featureDataMap.get("Comments") );
        $(subject).pressEnter();
        commonMethods.waitForElementExplicitly(2000);
        MailPage mailPage = new MailPage();
        if(featureDataMap.get("Mail_Attribute").equals("default")){
            mailPage.selectMailAttribute("Attribute 1", mailAttributeMap.get("attribute1"));
        } else {
            mailPage.selectMailAttribute("Attribute 1", featureDataMap.get("Mail_Attribute"));
        }
        $(reasonForIssueSelectBox).selectOption(1);

        $(sendBtn).click();
        driver.switchTo().defaultContent();

    }
}
