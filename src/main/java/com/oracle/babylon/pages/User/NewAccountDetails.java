package com.oracle.babylon.pages.User;

import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

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
    private By job_title_txt_box = By.xpath("//div[@id='position']//input");
    private By saveBtn = By.id("btnSave");

    public void verifyPage() {
        //Added the code directly because page does not have a frame
        commonMethods.waitForElementExplicitly(3000);
        String headerName = $(header).text();
        Assert.assertTrue(headerName.contains("Welcome to your new account"));

    }

    /**
     * Function to fill the details of the user
     *
     * @param userDetailsMap
     */
    public void fillUserDetails(Map<String, String> userDetailsMap) {
        if ($(titleDrpDwn).isDisplayed()) {
            Select select = new Select($(titleDrpDwn));
            select.selectByValue(userDetailsMap.get("Title"));
            select = new Select($(jobFunctionDrpDwn));
            $(jobFunctionDrpDwn).click();
            select.selectByVisibleText(userDetailsMap.get("Job_Function"));
            $(jobFunctionDrpDwn).pressEscape();
            $(job_title_txt_box).click();
            $(job_title_txt_box).sendKeys(userDetailsMap.get("Job_Title"));
            $(saveBtn).click();
        } else {
            System.out.println("User details are already filled");
        }
    }

}
