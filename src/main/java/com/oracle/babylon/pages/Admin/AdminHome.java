package com.oracle.babylon.pages.Admin;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Class file to contain all the methods related the home page when logged in as Administrator
 * Author : susgopal
 */
public class AdminHome extends Navigator {

    public AdminHome(){
        this.driver = WebDriverRunner.getWebDriver();
    }

    private CommonMethods commonMethods = new CommonMethods();

    /**
     * Method to verify for the title of the page
     */
    public void verifyPage() {
       // commonMethods.switchToFrame(driver, "frameMain");
        Assert.assertTrue(verifyPageTitle("Welcome to Aconex"));
       // switchTo().defaultContent();
    }
}
