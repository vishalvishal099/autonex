package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains common methods related to Mails
 * Author : susgopal
 */
public class MailPage {
    //Object and reference assignment
    WebDriver driver = WebDriverRunner.getWebDriver();
    Navigator navigator = new Navigator();

    //Initializing the web elements
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By mailNoTextBox = By.id("rawQueryText");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By sendBtn = By.xpath("//button[@id='btnSend']");
    private By mailNo = By.xpath("//div[@class='mailHeader-numbers']//div[2]//div[2]");
    private By mailNoFromTable = By.xpath("//td[@class='column_documentNo']");

    /**
     * Function to search mails by the mail number search key
     *
     * @param mail_number
     */
    public void searchMailNumber(String mail_number) {
        $(mailNoTextBox).sendKeys(mail_number);
        $(searchBtn).click();
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.waitForElementExplicitly(500);
        $(loadingIcon).should(disappear);
    }

    /**
     * Return the number of rows when we search for the mail
     *
     * @return
     */
    public int searchResultCount() {
        return driver.findElements(By.cssSelector(".dataRow")).stream().filter(e -> e.isDisplayed()).collect(Collectors.toList()).size();
    }

    /**
     * Retrieve the mail using the mail number as the key
     *
     * @return the mail number of the
     */
    public String getMailNumber() {
        return driver.findElement(mailNoFromTable).getText();
    }

    /**
     * Function to click on Send button
     *
     * @return
     */
    public String send() {
        $(sendBtn).click();
        $(loadingIcon).should(disappear);
        String mailNumber = $(mailNo).getText();
        return mailNumber;
    }

}
