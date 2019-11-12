package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to Compose Mail
 * Author : vsinghsi
 */
public class ComposeMail {

    //Initializing the objects and assigning references to it
    WebDriver driver = WebDriverRunner.getWebDriver();
    private User user = null;
    Navigator navigator = new Navigator();
    DataStore dataStore = new DataStore();

    //Initializing the Web Elements
    private By mailNumberField = By.xpath("//div[@class='mailHeader-numbers']//div[2]//div[2]");
    private By to_mailId = By.xpath("//input[@name='SPEED_ADDRESS_TO']");
    private By sendBtn = By.xpath("//button[@id='btnSend']");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By correspondenceTypeId = By.id("Correspondence_correspondenceTypeID");
    private By attributeClickOk = By.xpath("//button[@id='attributePanel-commit' and @title='OK']");
    private By primaryAttributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");
    private By attributeAddButton = By.xpath("//button[@id='attributeBidi_PRIMARY_ATTRIBUTE_add']");

    /**
     * Function to navigate to a sub menu from the Aconex home page
     */
    public void selectMenuSubMenu() {
        navigator.getMenuSubmenu("Mail", "Blank Mail");
        CommonMethods commonMethods = new CommonMethods();
        driver = commonMethods.switchToFrame(driver, "frameMain");
    }

    /**
     * Function used to compose the mail.
     *
     * @param user1
     * @param data
     */
    public void composeMail(String user1, String data) {
        //Fetch the table from the data store
        Map<String, String> table = dataStore.getTable(data);
        //According to the keys passed in the table, we select the fields
        for (String tableData : table.keySet()) {
            CommonMethods commonMethods = new CommonMethods();
            switch (tableData) {
                case "Mail Type":
                    selectMailType(table.get(tableData));
                    break;
                case "Subject":
                    fillInSubject(table.get(tableData));
                    break;
                case "Attribute 1":
                    commonMethods.selectAttribute("Attribute 1", table.get(tableData));
                    break;
                case "Mail Body":
                    setMailBody(table.get(tableData));
                    break;

            }
        }
    }

    /**
     * Function to select the mail type while composing the mail
     *
     * @param mailType input for correspondence type id
     */
    public void selectMailType(String mailType) {
        $(correspondenceTypeId).selectOption(mailType);
    }


    /**
     * Enter the subject required while composing the mail
     *
     * @param subject to be entered
     */
    public void fillInSubject(String subject) {
        $(By.id("Correspondence_subject")).setValue(subject);
    }


    /**
     * Enter the mail body by switching the frame
     *
     * @param mailBody
     */
    public void setMailBody(String mailBody) {
        switchToMailBodyFrame();
        $(".cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders").setValue(mailBody);
    }

    /**
     * Switch the mail body frame
     */
    public void switchToMailBodyFrame() {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        switchTo().frame(0);
    }

    /**
     * Enter to user list to send the mail to
     *
     * @param userTo string of users that we want to send the mail to
     */
    public void fillTo(String userTo) {
        user = dataStore.getUser(userTo);
        userTo = user.getFullName();
        CommonMethods commonMethods = new CommonMethods();
        driver = commonMethods.switchToFrame(driver, "frameMain");
        $(to_mailId).setValue(userTo);
        $(to_mailId).pressEnter();
        driver = commonMethods.waitForElement(driver, to_mailId);
        $(to_mailId).click();
    }

    /**
     * Method to send the mail
     *
     * @return mail numbers for the mail sent
     */
    public String send() {
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.waitForElementExplicitly(3000);
        $(sendBtn).click();
        $(loadingIcon).should(disappear);
        commonMethods.waitForElement(driver, mailNumberField, 3000);
        String mailNumber = $(mailNumberField).getText();
        return mailNumber;
    }


}
