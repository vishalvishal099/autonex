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
    
    //Identifier at the top of the page

    public void sendMail() {
        $(By.xpath("//button[@class='auiButton primary ng-binding ng-scope']"));
    }

//Check if common code can be written for code under switch statements and reused.
    public void verifyPreview(String mailAttribute) {
//        commonMethods.switchToFrame(driver, "frameMain");
        Map<String, String> table = dataStore.getTable(mailAttribute);
        //According to the keys passed in the table, we select the fields
        for (String tableData : table.keySet()) {
            switch (tableData) {
                case "To":
                    String[] namesListForTo = table.get(tableData).split(",");
                    for (String name : namesListForTo) {
                        String toUser = $(By.xpath("//li[@class='mailRecipients-firstRecipient']")).text();
                        Assert.assertTrue(toUser.contains(name));
                    }
                    break;
                case "Subject":
                    String subject = $(By.xpath("//div[@class='mailHeader-subject']")).text();
                    Assert.assertTrue(subject.contains(table.get(tableData)));
                    break;
                case "Mail Type":
                    String mailType = $(By.xpath("//div[@class='mailHeader-value']")).text();
                    Assert.assertTrue(mailType.contains(table.get(tableData)));
                    break;
                case "Attachment":
                    String attachment = $(By.xpath("//table[@class='auiTable ng-scope']//tbody")).text();
                    Assert.assertTrue(attachment.contains(table.get(tableData)));
                    break;
                case "File Type":
                    String attachmentType = $(By.xpath("//table[@class='auiTable ng-scope']//tbody")).text();
                    Assert.assertTrue(attachmentType.contains(table.get(tableData)));
                    break;
            }
        }
        driver.switchTo().defaultContent();
    }

    public void verifyNoError() {
        //Is try catch block necessary. Method will not stop the test if error is present. It will only print the message.
        try {
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            //any other stuff
        } catch (Exception e) {
            System.out.println("No Error Message" + e.getMessage());
        }
    }

    public void verifyButton(String button) {
        switch (button.toLowerCase()) {
            //Remove unwanted comments
            //Identifier should be defined on top
            case "print"://button[contains(text(),'Print')]
                Assert.assertFalse($(By.xpath("//button[contains(text(),'Print')]")).exists());
        }
    }

    public void verifyDocumentDetails(String document) {
        commonMethods.waitForElementExplicitly(3000);
        $(By.xpath("//table[@class='auiTable']//tbody//tr[1]/td[1]/input")).click();
        $(By.xpath("//button[contains(text(),'View File Properties')]")).click();
        commonMethods.waitForElement(driver, By.xpath("//mail-document-properties-grid[@class='ng-isolate-scope']//tbody//tr[1]"));
        String docDetail = $(By.xpath("//mail-document-properties-grid[@class='ng-isolate-scope']//tbody//tr[1]")).text();
        Assert.assertTrue(docDetail.contains(document));

    }

    public void markAsUnread(){
        $(By.xpath("//aui-menu-button[@class='ng-scope ng-isolate-scope']//button[@class='auiMenuButton auiButton ng-binding'][contains(text(),'Actions')]")).click();
        $(By.xpath("//a[@class='ng-binding ng-scope']")).click();
    }

}

