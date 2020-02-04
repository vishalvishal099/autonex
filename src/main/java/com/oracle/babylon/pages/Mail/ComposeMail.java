package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.pages.Directory.DirectoryPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import com.oracle.babylon.pages.Document.DocumentPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to Compose Mail
 * Author : vsinghsi
 */
public class ComposeMail extends MailPage {

    //Initializing the objects and assigning references to it


    public ComposeMail() {
        this.driver = WebDriverRunner.getWebDriver();
    }

    private DocumentPage documentPage = new DocumentPage();
    private DirectoryPage directoryPage = new DirectoryPage();
    //Initializing the Web Elements
    private By mailNumberField = By.xpath("//div[@class='mailHeader-numbers']//div[2]//div[2]");
    private By to_mailId = By.xpath("//input[@name='SPEED_ADDRESS_TO']");
    private By sendBtn = By.xpath("//button[@id='btnSend']");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By attachBtn = By.xpath("//button[@id='btnMailAttachments']//div[@class='uiButton-label'][contains(text(),'Attach')]");
    private By option = By.xpath("//div[contains(text(),'Options')]");
    private By manualMailNumber = By.xpath("//input[@id='radSpecifyMailNoManually']");
    private By attachmentContainer = By.xpath("//td[@id='corrAttachmentsContainer']");
    private By mailSubject = By.id("Correspondence_subject");
    private By selectUser = By.xpath("//table[@id='resultTable']//tbody//tr[1]//input[@name='USERS_LIST']");
    private By directory = By.xpath("//body/div[@id='page']/form[@id='form1']/div[@id='main']/div[@id='mockdoc']/div[@class='box']/table[@id='heroSection']/tbody//tr[1]//button[1]//div[1]//div[1]");
    private By docContainer = By.xpath("//td[@id='corrAttachmentsContainer']");
    private By saveToDraftBtn = By.xpath("//div[contains(text(),'Save To Draft')]");

    /**
     * Function to navigate to a sub menu from the Aconex home page
     */
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Mail", "Blank Mail");
        verifyPageTitle("New Mail");
    }

    /**
     * Function used to compose the mail.
     *
     * @param user1
     * @param data
     */
    public void composeMail(String user1, String data) {
        //Fetch the table from the data store
        commonMethods.switchToFrame(driver, "frameMain");
        Map<String, String> table = dataStore.getTable(data);
        //According to the keys passed in the table, we select the fields
        for (String tableData : table.keySet()) {
            switch (tableData) {
                case "To":
                case "Cc":
                case "Bcc":
                    String[] namesListForGroup = table.get(tableData).split(",");
                    addUserToMailingList(namesListForGroup, tableData);
                    break;
                case "Mail Type":
                    selectMailType(table.get(tableData));
                    break;
                case "Subject":
                    fillInSubject(table.get(tableData));
                    break;
                case "Attribute 1":
                case "Attribute 2":
                case "Attribute 3":
                case "Attribute 4":
                    selectMailAttribute(tableData, table.get(tableData));
                    break;
                case "Mail Body":
                    setMailBody(table.get(tableData));
                    break;
                case "Attachment":
                    attachDocument(table.get(tableData));
                    break;
            }
        }
        driver.switchTo().defaultContent();
    }

    public void addUserToMailingList(String[] namesListForGroup, String group){JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        if (!currentFrame.equalsIgnoreCase("main")) {
            commonMethods.switchToFrame(driver, "frameMain");
        }
        for (String name : namesListForGroup) {
            commonMethods.waitForElement(driver, directory);
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
            $(directory).click();
            String[] username = name.split("\\s+");
            directoryPage.fillFieldsAndSearch(username[0],username[1],null,null,null);
            commonMethods.waitForElementExplicitly(1000);
            directoryPage.selectUser();
            directoryPage.selectRecipientGroup(group);
            directoryPage.clickOkBtn();
        }
    }


    /**
     * Enter the subject required while composing the mail
     *
     * @param subject to be entered
     */
    public void fillInSubject(String subject) {
        commonMethods.waitForElement(driver, to_mailId);
        $(mailSubject).setValue(subject);
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
        String[] namesListForGroup = userTo.split(",");
        addUserToMailingList(namesListForGroup, "To");
    }


    /**
     * Method to send the mail
     *
     * @return mail numbers for the mail sent
     */
    public String send() {
        commonMethods.waitForElementExplicitly(3000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        if (!currentFrame.equalsIgnoreCase("main")) {
            driver.switchTo().defaultContent();
        }
        $(sendBtn).click();
        $(loadingIcon).should(disappear);
        commonMethods.waitForElementExplicitly(5000);
        String mailNumber = $(mailNumberField).getText();
        return mailNumber;
    }

    public String userDefinedMailNumber() {
        commonMethods.switchToFrame(driver, "frameMain");
        $(option).click();
        Faker faker = new Faker();
        Map<String, String> userDetail = new HashMap<>();
        String mailNumber = "Mail-" + faker.number().digits(6);
        WebElement checkBox = driver.findElement(By.xpath("//input[@id='radSpecifyMailNoManually']"));
        if (checkBox.isSelected()) {
            $(By.xpath("//input[@id='Correspondence_documentNo']")).sendKeys(mailNumber);
        } else {
            checkBox.click();
            $(By.xpath("//input[@id='Correspondence_documentNo']")).sendKeys(mailNumber);
        }
        $(By.xpath("//button[@id='btnOptionsOk']//div[@class='uiButton-label'][contains(text(),'OK')]")).click();
        return mailNumber;
    }

    public void saveToDraft() {
        commonMethods.waitForElementExplicitly(3000);
        $(saveToDraftBtn).click();

    }

    public void attachDocumentUsingFullSearch(String document) {
        commonMethods.waitForElement(driver, attachBtn);
        $(attachBtn).click();
        $(By.xpath("//a[contains(text(),'Document')]")).click();
        commonMethods.waitForElementExplicitly(2000);
        documentPage.searchDocumentNo(document);
        $(By.xpath("//div[@id='searchResultsWrapper']//td[1]//input[@name='selectedIdsInPage']")).click();
        $(By.xpath("//div[contains(text(),'Attach File')]")).click();
        commonMethods.waitForElement(driver, attachmentContainer);
//        Assert.assertTrue($(attachmentContainer).text().contains(document));
    }

    public void attachDocument(String documentDetail) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        if (!currentFrame.equalsIgnoreCase("main")) {
            driver.switchTo().defaultContent();
        }
        commonMethods.waitForElement(driver, attachBtn);
        $(attachBtn).click();
        $(By.xpath("//a[contains(text(),'Document')]")).click();
        commonMethods.waitForElementExplicitly(3000);
        $(By.xpath("//div[contains(text(),'Open Full Search Page')]")).click();
        documentPage.searchDocumentNo(documentDetail);
        $(By.xpath("//div[@id='searchResultsWrapper']//td[1]//input[@name='selectedIdsInPage']")).click();
        $(By.xpath("//div[contains(text(),'Attach File')]")).click();
        commonMethods.waitForElement(driver, attachmentContainer);
//        Assert.assertTrue($(attachmentContainer).text().contains(documentDetail));
    }

    public void removeUserFromMailingList(String group, String user) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        if (!currentFrame.equalsIgnoreCase("main")) {
            commonMethods.switchToFrame(driver, "frameMain");
        }
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
        switch (group.toLowerCase()) {
            case "to":
                $(By.xpath("//div[@id='recipientsTO']//tr[contains(., '" + user + "')]//td[3]/div")).click();
                break;
            case "cc":
                $(By.xpath("//div[@id='recipientsCC']//tr[contains(., '" + user + "')]//td[3]/div")).click();
                break;
            case "bcc":
                $(By.xpath("//div[@id='recipientsBCC']//tr[contains(., '" + user + "')]//td[3]/div")).click();
                break;
        }
        commonMethods.waitForElementExplicitly(2000);
    }

    public void previewMail() {
        $(By.xpath("//div[contains(text(),'Preview')]")).click();
    }

    public void verifyValidationMessage() {
        commonMethods.waitForElement(driver, docContainer);
        String message = $(docContainer).text();
        Assert.assertTrue(message.contains("This attachment has no associated file"));
    }
}
