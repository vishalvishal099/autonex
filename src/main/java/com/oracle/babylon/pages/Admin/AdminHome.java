package com.oracle.babylon.pages.Admin;

import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class file to contain all the methods related the home page when logged in as Administrator
 * Author : susgopal
 */
public class AdminHome extends Navigator {

    public AdminHome(WebDriver driver){
        this.driver = driver;
    }

    //Initialization of the web elements
    private By setupBtn = By.id("nav-bar-SETUP");
    private By searchLink = By.id("nav-bar-SETUP-SETUP-ADMINSEARCH");
    private By toolsLink = By.id("nav-bar-SETUP-SETUP-ADMINTOOLS");

    private CommonMethods commonMethods = new CommonMethods();

    /**
     * Click the Setup Button to view the options available
     * @throws InterruptedException
     */
    public void clickSetupBtn() throws InterruptedException {
        Thread.sleep(3000);
        commonMethods.waitForElement(driver, setupBtn, 5);
        $(setupBtn).click();
    }

    /**
     * Click on the Search Button to retrieve project/organization details
     */
    public void clickSearchBtn(){
        commonMethods.waitForElement(driver, searchLink, 5);
        $(searchLink).click();

    }

    /**
     *
     * Click on the tools link to add or modify some configurations
     */
    public void clickToolsBtn(){
        commonMethods.waitForElement(driver, toolsLink, 5);
        $(toolsLink).click();
    }
}
