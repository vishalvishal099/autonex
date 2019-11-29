package com.oracle.babylon.pages.Mail;

import com.oracle.babylon.Utils.helper.Navigator;

import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class ViewMail extends Navigator {

    private By mailNumber = By.xpath("//div[@data-automation-id='mailHeader-number-value']");
    private By mailType = By.xpath("//div[@data-automation-id='mailHeader-type-value']//span");

    public String retrieveMailNumber(){
        commonMethods.switchToFrame(driver, "frameMain");
        String mailNumberText = $(mailNumber).getText();
        driver.switchTo().defaultContent();
        return mailNumberText;
    }

    public String retrieveMailType(){
        commonMethods.switchToFrame(driver, "frameMain");
        String mailTypeText = $(mailType).getText();
        driver.switchTo().defaultContent();
        return mailTypeText;
    }
}
