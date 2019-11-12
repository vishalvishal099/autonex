package com.oracle.babylon.pages.User;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.codeborne.selenide.Selenide.$;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

/**
 * Class to fill the details of the user during organization registration page
 * Author : susgopal
 */
public class UserDetails {

    //Initialization of the Web Elements
    private By titleDrpDwn = By.id("userTitle");
    private By jobFunctionDrpDwn = By.id("jobFunction");
    private By job_title_txt_box = By.xpath("//div[@id='position']//input");
    private By saveBtn = By.id("btnSave");


    /**
     * Function to fill the details of the user
     * @param userDetailsMap
     */
    public void fillUserDetails(Map<String, String> userDetailsMap){
        Select select = new Select($(titleDrpDwn));
        select.selectByValue(userDetailsMap.get("Title"));
        select = new Select($(jobFunctionDrpDwn));
        $(jobFunctionDrpDwn).click();
        select.selectByVisibleText(userDetailsMap.get("Job_Function"));
        $(jobFunctionDrpDwn).pressEscape();
        $(job_title_txt_box).click();
        $(job_title_txt_box).sendKeys(userDetailsMap.get("Job_Title"));
        $(saveBtn).click();
    }

}
