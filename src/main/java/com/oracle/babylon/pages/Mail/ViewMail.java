package com.oracle.babylon.pages.Mail;

import com.oracle.babylon.Utils.helper.Navigator;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.invoke.SwitchPoint;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

public class ViewMail extends Navigator {

    private By mailNumber = By.xpath("//div[@data-automation-id='mailHeader-number-value']");
    private By mailType = By.xpath("//div[@data-automation-id='mailHeader-type-value']//span");
    private By edit = By.xpath("//button[contains(text(),'Edit')]");
    private By printBtn = By.xpath("//button[contains(text(),'Print')]");
    private By documentDetail = By.xpath("//mail-document-properties-grid[@class='ng-isolate-scope']//tbody//tr[1]");
    private By actionBtn = By.xpath("//aui-menu-button[@class='ng-scope ng-isolate-scope']//button[@class='auiMenuButton auiButton ng-binding'][contains(text(),'Actions')]");
    private By attachmentSection = By.xpath("//table[@class='auiTable ng-scope']//tbody");
    private By mailTypeValue = By.xpath("//div[@class='mailHeader-value']");
    private By mailSubject = By.xpath("//div[@class='mailHeader-subject']");
    private By recipient = By.xpath("//li[@class='mailRecipients-firstRecipient']");

    public String retrieveMailNumber() {
        commonMethods.switchToFrame(driver, "frameMain");
        String mailNumberText = $(mailNumber).getText();
        driver.switchTo().defaultContent();
        return mailNumberText;
    }

    public String retrieveMailType() {
        commonMethods.switchToFrame(driver, "frameMain");
        String mailTypeText = $(mailType).getText();
        driver.switchTo().defaultContent();
        return mailTypeText;
    }

    public void editMail() {
        commonMethods.waitForElementExplicitly(5000);
        $(edit).click();
    }

    public void sendMail() {
        $(By.xpath("//button[@class='auiButton primary ng-binding ng-scope']"));
    }

    public void verifyPreview(String mailAttribute) {
//        commonMethods.switchToFrame(driver, "frameMain");
        Map<String, String> table = dataStore.getTable(mailAttribute);
        //According to the keys passed in the table, we select the fields
        for (String tableData : table.keySet()) {
            switch (tableData) {
                case "To":
                    String[] namesListForTo = table.get(tableData).split(",");
                    for (String name : namesListForTo) {
                        String toUser = $(recipient).text();
                        Assert.assertTrue(toUser.contains(name));
                    }
                    break;
                case "Subject":
                    String subject = $(mailSubject).text();
                    Assert.assertTrue(subject.contains(table.get(tableData)));
                    break;
                case "Mail Type":
                    String mailType = $(mailTypeValue).text();
                    Assert.assertTrue(mailType.contains(table.get(tableData)));
                    break;
                case "Attachment":
                    String attachment = $(attachmentSection).text();
                    Assert.assertTrue(attachment.contains(table.get(tableData)));
                    break;
                case "File Type":
                    String attachmentType = $(attachmentSection).text();
                    Assert.assertTrue(attachmentType.contains(table.get(tableData)));
                    break;
            }
        }
        driver.switchTo().defaultContent();
    }

    public void verifyNoError() {
        try {
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            System.out.println("Error Present Present");
            System.exit(1);
            //any other stuff
        } catch (Exception e) {
            System.out.println("Error Not Present Present");
        }
    }

    public void verifyButton(String button) {
        switch (button.toLowerCase()) {
            case "print":
                Assert.assertFalse($(printBtn).exists());
        }
    }

    public void verifyDocumentDetails(String document) {
        commonMethods.waitForElementExplicitly(3000);
        $(By.xpath("//table[@class='auiTable']//tbody//tr[1]/td[1]/input")).click();
        $(By.xpath("//button[contains(text(),'View File Properties')]")).click();
        commonMethods.waitForElement(driver, documentDetail);
        String docDetail = $(documentDetail).text();
        Assert.assertTrue(docDetail.contains(document));

    }

    public void markAsUnread(){
        $(actionBtn).click();
        $(By.xpath("//a[@class='ng-binding ng-scope']")).click();
    }

}

