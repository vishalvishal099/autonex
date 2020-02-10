package com.oracle.babylon.pages.User;

import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class to fill the details of the user during organization registration page
 * Author : susgopal
 */
public class NewAccountDetails extends Navigator {

    //Initialization of the Web Elements
    private By titleDrpDwn = By.id("userTitle");
    private By jobFunctionDrpDwn = By.id("jobFunction");
    private By language = By.xpath("//select[@id='selLanguages']");
    private By job_title_txt_box = By.xpath("//div[@id='position']//input");
    private By saveBtn = By.id("btnSave");
    private By password = By.xpath("//div[@id='password']//input[@class='uiPasswordField-input']");
    private By confirmPassword = By.xpath("//div[@id='passwordConfirm']//input[@class='uiPasswordField-input']");
    private By directoryListing = By.xpath("//input[@id='globalDirectoryOptIn_radio']");
    private Faker faker = new Faker(new Locale("en-US"));


    public void verifyPage() {
        //Added the code directly because page does not have a frame
        commonMethods.waitForElementExplicitly(3000);
//        commonMethods.switchToFrame(driver, "frameMain");
        String headerName = $(header).text();
        Assert.assertTrue(headerName.contains("Welcome to your new account"));

    }

    /**
     * Function to fill the details of the user
     *
     * @param userDetailsMap
     */
    public void fillUserDetails(Map<String, String> userDetailsMap, String userId) {
        if ($(titleDrpDwn).isDisplayed()) {
            Select select = new Select($(titleDrpDwn));
            select.selectByValue(userDetailsMap.get("Title"));
            select = new Select($(jobFunctionDrpDwn));
            $(jobFunctionDrpDwn).click();
            select.selectByVisibleText(userDetailsMap.get("Job_Function"));
            $(jobFunctionDrpDwn).pressEscape();
            $(job_title_txt_box).click();
            $(job_title_txt_box).sendKeys(userDetailsMap.get("Job_Title"));
            $(language).selectOptionContainingText(userDetailsMap.get("Language"));
            if ($(password).exists()) {
                String password = faker.internet().password() + "8";
                $(password).sendKeys(password);
                $(confirmPassword).sendKeys(password);
                Map<String, Map<String, String>> mapOfMap = new Hashtable<>();
                String[] keys = {"username", "password", "full_name"};
                Map<String, String> valueMap = new Hashtable<>();
                valueMap.put(keys[1],password);
                mapOfMap.put(userId, valueMap);
                dataSetup.convertMapOfMapAndWrite(userId, mapOfMap, userDataPath);
            }
            if ($(directoryListing).exists()) {
                $(directoryListing).click();
            }
            $(saveBtn).click();
            commonMethods.waitForElementExplicitly(2000);

        } else {
            System.out.println("User details are already filled");
        }
    }

}
