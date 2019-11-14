package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class to hold the functions related to the the mail inbox page
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

    /**
     * Function to navigate to a sub menu from the Aconex home page
     */
    public void selectMenuSubMenu() {
        getMenuSubmenu("Mail", "Inbox");
        this.driver = commonMethods.switchToFrame(driver, "frameMain");
    }

    /**
     * Search the mail using the mail number key
     *
     * @param mail_number
     */
    public void searchMailNumber(String mail_number) {

        $(mailNumberTextBox).sendKeys(mail_number);
        commonMethods.waitForElementExplicitly(2000);
        $(searchBtn).click();
        //Wait for the results to be retrieved
        By mailNumberSpan = By.xpath("//span[text()='" + mail_number + "']");
        commonMethods.waitForElement(this.driver, mailNumberSpan, 5000);
        $(loadingIcon).should(disappear);

    }

}
