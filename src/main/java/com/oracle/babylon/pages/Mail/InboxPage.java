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

    public InboxPage(){
        this.driver = WebDriverRunner.getWebDriver();
    }

    //Initializing the web elements
    private By mailNumberTextBox = By.id("rawQueryText");
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By pageTitle = By.xpath("//h1//span[text()='Search Mail']");
    private By myMailOnlyChkbox = By.xpath("//span[contains(text(),'My mail only')]//././../input");
    private By recipientTypeTo = By.xpath("//span[text()='To']//././..//input");
    private By recipientTypeCc = By.xpath("//span[text()='Cc']//././..//input");
    private By recipientTypeAny = By.xpath("//span[text()='Any']//././..//input");
    private By loggedInUserName = By.xpath("//span[@class='nav-userDetails']");
    private By recipientName = By.xpath("//table[@id='resultTable']//tbody//tr[1]//td[8]//span");

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
        commonMethods.waitForElementExplicitly(2000);
        $(searchBtn).click();
        //Wait for the results to be retrieved
        By mailNumberSpan = By.xpath("//span[text()='" + mail_number + "']");
        commonMethods.waitForElement(this.driver, mailNumberSpan, 5000);
        $(loadingIcon).should(disappear);

    }

    public String getUserName()
    {
        return driver.findElement(loggedInUserName).getText();

    }

    public String getRecipientName()
    {
        commonMethods.switchToFrame(driver,"frameMain");
        return $(recipientName).getText();


    }

    public void verifyToAndCcAndAny(String user, String option)
    {
        commonMethods.switchToFrame(driver,"frameMain");
        $(myMailOnlyChkbox).click();
        if(option.equals("cc"))
        {
            $(recipientTypeCc).click();

        }
        else if(option.equals("to"))
        {
            $(recipientTypeTo).click();

        }
        else if(option.equals("any"))
        {
            $(recipientTypeAny).click();
        }

        driver.switchTo().defaultContent();
    }
}


