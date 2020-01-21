package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class to hold the methods related to the the mail inbox page
 * Author : visinghsi
 */
public class InboxPage extends MailPage {

    public InboxPage() {
        this.driver = WebDriverRunner.getWebDriver();
    }

    //Initializing the web elements
    private By mailNumberTextBox = By.id("rawQueryText");
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By pageTitle = By.xpath("//h1//span[text()='Search Mail']");

    /**
     * Function to navigate to a sub menu from the Aconex home page
     */
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Mail", "Inbox");
        verifyPageTitle(pageTitle);
    }

    /**
     * Search the mail using the mail number key
     *
     * @param mail_number
     */
    public void searchMailNumber(String mail_number) {
        commonMethods.switchToFrame(driver, "frameMain");
        $(mailNumberTextBox).sendKeys(mail_number);
        $(searchBtn).click();
        commonMethods.waitForElementExplicitly(2000);
        //Wait for the results to be retrieved
//        By mailNumberSpan = By.xpath("//span[text()='" + mail_number + "']");
//        commonMethods.waitForElement(this.driver, mailNumberSpan, 5000);
//        $(loadingIcon).should(disappear);
    }

    public void openEmail() {
        $(By.xpath("//tbody[@id='rowPerMailTableBody']//tr[1]//td[4]/a")).click();
    }

    public void markAsRead() {
        $(By.xpath("//div[@class='pull-left ng-scope ng-isolate-scope']//button[@class='auiMenuButton auiButton dropdown-toggle']")).click();
        $(By.xpath("//div[@class='pull-left ng-scope ng-isolate-scope open']//ul[@class='dropdown-menu']//a[contains(text(),'Mark as Read')]")).click();
    }

}

