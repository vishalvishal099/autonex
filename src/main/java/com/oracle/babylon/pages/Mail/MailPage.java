package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains common methods related to Mails
 * Author : susgopal
 */
public class MailPage extends Navigator {

    //Initializing the web elements
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By mailNoTextBox = By.id("rawQueryText");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By sendBtn = By.xpath("//button[@id='btnSend']");
    private By mailNo = By.xpath("//div[@class='mailHeader-numbers']//div[2]//div[2]");
    private By mailNoFromTable = By.xpath("//td[@class='column_documentNo']");
    private By primaryAttributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");
    private By attributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");
    private By thirdAttributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");
    private By fourthAttributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");

    private By attributeAddButton = By.xpath("//button[@id='attributeBidi_PRIMARY_ATTRIBUTE_add']");
    private By correspondenceTypeId = By.id("Correspondence_correspondenceTypeID");

    /**
     * Function to search mails by the mail number search key
     *
     * @param mail_number
     */
    public void searchMailNumber(String mail_number) {
        commonMethods.switchToFrame(driver, "frameMain");
        $(mailNoTextBox).sendKeys(mail_number);
        $(searchBtn).click();
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
     * Function to select the mail type while composing the mail
     *
     * @param mailType input for correspondence type id
     */
    public void selectMailType(String mailType) {
        $(correspondenceTypeId).selectOption(mailType);
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



    /**
     * Function to select the field attribute*
     *
     * @param attributeIdentifier key to be searched
     * @param value               the value that needs to be selected
     */
    public void selectMailAttribute(String attributeIdentifier, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver = WebDriverRunner.getWebDriver();
        //Selecting attribute from the left panel to the right panel.Click on OK after it is done.
        String attributelocator = "//tr[td[label[contains(text(),'" + attributeIdentifier + "')]]]/td[@class='contentcell']/div";
        driver = commonMethods.waitForElement(driver, (By.xpath(attributelocator)));
        js.executeScript("window.scrollBy(0,-1000)", "");
        $(By.xpath(attributelocator)).click();
        String attributeLocator1 = "//div[@id='";
        String attributeLocator2 = "']//div[@class='uiBidi-left']//select";
        switch (attributeIdentifier) {
            case "Attribute 1":
                String locator1 = attributeLocator1 + "attributeBidi_PRIMARY_ATTRIBUTE" + attributeLocator2;
                driver = commonMethods.waitForElement(driver, (By.xpath(locator1)));
                $(By.xpath(locator1 + "//option[contains(text(),'" + value + "')]")).doubleClick();
                break;
            case "Attribute 2":
                String locator2 = attributeLocator1 + "attributeBidi_SECONDARY_ATTRIBUTE" + attributeLocator2;
                driver = commonMethods.waitForElement(driver, (By.xpath(locator2)));
                $(By.xpath(locator2 + "//option[contains(text(),'" + value + "')]")).doubleClick();
                break;
            case "Attribute 3":
                String locator3 = attributeLocator1 + "attributeBidi_THIRD_ATTRIBUTE" + attributeLocator2;
                driver = commonMethods.waitForElement(driver, (By.xpath(locator3)));
                $(By.xpath(locator3 + "//option[contains(text(),'" + value + "')]")).doubleClick();
                break;
            case "Attribute 4":
                String locator4 = attributeLocator1 + "attributeBidi_FOURTH_ATTRIBUTE" + attributeLocator2;
                driver = commonMethods.waitForElement(driver, (By.xpath(locator4)));
                $(By.xpath(locator4 + "//option[contains(text(),'" + value + "')]")).doubleClick();
                break;
        }
        driver = commonMethods.waitForElement(driver, attributeAddButton);
        $(attributeAddButton).click();
        driver = commonMethods.waitForElement(driver, By.xpath("//button[@id='attributePanel-commit' and @title='OK']"));
        selectAttributeClickOK();
    }

}
