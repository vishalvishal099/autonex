package com.oracle.babylon.pages.Document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Directory.DirectoryPage;
import com.oracle.babylon.pages.Mail.MailPage;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import java.io.IOException;
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
    public void createBasicTransmittal(DataTable dataTable) throws IOException, ParseException {
        Map<String, String> featureDataMap = dataTable.transpose().asMap(String.class, String.class);
        //The data is taken from userData.json file and we search for the project in admin tool
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        //Project info
        Map<String, String> mailAttributeMap = mapOfMap.get("mailattribute");
        $(directoryBtn).click();
        commonMethods.clickListToChange(By.xpath("//h1[text()='Search - Directory']"), "Global");
        String full_name = featureDataMap.get("Full_Name");
        String groupName = full_name.split(" ")[0];
        String familyName = full_name.split(" ")[1];
        directoryPage.fillFieldsAndSearch(groupName, familyName, null, null, null);
        directoryPage.selectToRecipient();
        directoryPage.clickOkBtn();
        $(subject).sendKeys(featureDataMap.get("Comments") );
        $(subject).pressEnter();
        MailPage mailPage = new MailPage();
        if(featureDataMap.get("Mail_Attribute").equals("default")){
            mailPage.selectMailAttribute("Attribute 1", mailAttributeMap.get("attribute1"));
        } else {
            mailPage.selectMailAttribute("Attribute 1", featureDataMap.get("Mail_Attribute"));
        }
        $(reasonForIssueSelectBox).selectOption(1);

        $(sendBtn).click();

    }
}
