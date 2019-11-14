package com.oracle.babylon.pages.Document;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Mail.MailPage;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import java.io.IOException;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains function to enable a user to create a transmittal
 */
public class TransmittalPage extends Navigator {

    //Initialization of Web Elements
    private By toIdTextBox = By.xpath("//input[@name='SPEED_ADDRESS_TO']");
    private By subject = By.id("Correspondence_subject");
    private By sendBtn = By.xpath("//button[@id='btnSend']");

    /**
     *  Create a transmittal with minimal data. To id, subject and attribute is provided.
     */
    public void createBasicTransmittal() throws IOException, ParseException {
        //The data is taken from userData.json file and we search for the project in admin tool
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> docMap = mapOfMap.get("document");
        //Project info
        Map<String, String> mailAttributeMap = mapOfMap.get("mailattribute");
        $(toIdTextBox).sendKeys(configFileReader.getEmailId());
        $(subject).sendKeys("Transmittal for " + docMap.get("docno") );
        $(subject).pressEnter();
        MailPage mailPage = new MailPage();
        mailPage.selectMailAttribute("Attribute 1",mailAttributeMap.get("attribute1"));
        $(sendBtn).click();

    }
}
