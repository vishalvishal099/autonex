package com.oracle.babylon.pages.Admin;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class file that contains method to perform any operations on the user
 * Author : susgopal
 */
public class UserInformation extends Navigator {

    //Initialization of Web Elements
    private By accountLockedChkBox = By.name("USER_LOCKED");
    private By accountDisabledChkBox = By.name("USER_DISABLED");
    private By saveBtn = By.id("btnSave");

    /**
     * Enable the user by unchecking the checbox for account disabled and locked
     */
    public void enableUser() {
        driver = WebDriverRunner.getWebDriver();
        commonMethods.switchToFrame(driver,"frameMain");
        $(accountLockedChkBox).click();
        $(accountDisabledChkBox).click();
        $(saveBtn).click();
        driver.switchTo().defaultContent();
    }

    /**
     * Method to verify for the title of the page
     */
    public void verifyPage() {
        driver = WebDriverRunner.getWebDriver();
        Assert.assertTrue(verifyPageTitle("User Information"));

    }
}
