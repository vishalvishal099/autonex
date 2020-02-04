package com.oracle.babylon.pages.Directory;

import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Contains the methods to perform operations on the Directory search page
 * Author : susgopal
 */
public class DirectoryPage extends Navigator {

    //Initialize all the identifier required in the page
    private By groupName = By.xpath("//input[@id='FIRST_NAME']");
    private By familyName = By.xpath("//input[@id='LAST_NAME']");
    private By searchBtn = By.xpath("//button[@id='btnSearch_page']");
    private By organizationName = By.xpath("//input[@id='ORG_NAME']");
    private By jobTitle = By.xpath("//input[@name='POSITION_NAME']");
    private By division = By.xpath("//input[@name='DIVISION_NAME']");
    private By userList = By.xpath("//input[@name='USERS_LIST']");
    private By addUserToBtn = By.xpath("//button[@id='btnAddTo_page']");
    private By okBtn = By.xpath("//button[@id='btnOk']");
    private By selectUser = By.xpath("//table[@id='resultTable']//tbody//tr[1]//input[@name='USERS_LIST']");

    /**
     * Method to input all the text boxes present in the directory search page
     *
     * @param groupNameVar
     * @param familyNameVar
     * @param organizatioNameVar
     * @param jobTitleVar
     * @param divisionVar
     */
    public void fillFieldsAndSearch(String groupNameVar, String familyNameVar, String organizatioNameVar, String jobTitleVar, String divisionVar) {
        //Checking if we need to set each text box value
        if (groupNameVar != null) {
            $(groupName).sendKeys(groupNameVar);
        }
        if (familyNameVar != null) {
            $(familyName).sendKeys(familyNameVar);
        }
        if (organizatioNameVar != null) {
            $(organizationName).sendKeys(organizatioNameVar);
        }
        if (jobTitleVar != null) {
            $(jobTitle).sendKeys(jobTitleVar);
        }
        if (divisionVar != null) {
            $(division).sendKeys(divisionVar);
        }
        searchBtnClick();

    }

    /**
     * Method to click the search button
     */
    public void searchBtnClick() {
        $(searchBtn).click();
    }

    /**
     * Method to click o retrieved row's entry
     */
    public void selectEntry() {
        $(userList).click();
    }

    /**
     * Method to click on the To Recipient button
     */
    public void clickToBtn() {
        $(addUserToBtn).click();
    }

    /**
     * Method to select the reciepient
     */
    public void selectToRecipient() {
        selectEntry();
        clickToBtn();
    }


    /**
     * Method to navigate to Project Directory and verify for the title of the page
     */
    public void navigateToProjectDirAndVerify() {
        getMenuSubmenu("Setup", "Project Directory");
        Assert.assertTrue(verifyPageTitle("Search - Directory"));
    }

    /**
     * Method to navigate to Global Directory and verify for the title of the page
     */
    public void navigateToGlobalDirAndVerify() {
        getMenuSubmenu("Setup", "Global Directory");
        Assert.assertTrue(verifyPageTitle("Search - Directory"));
    }


    /**
     * Method to click on the OK Button
     */
    public void clickOkBtn() {
        $(okBtn).click();
    }

    public void addRecipient(Map<String, String> map) {
        String full_name = map.get("Full_Name");
        String groupName = full_name.split(" ")[0];
        String familyName = full_name.split(" ")[1];
        fillFieldsAndSearch(groupName, familyName, null, null, null);
        selectToRecipient();
        clickOkBtn();
    }

    public void selectUser() {
        $(selectUser).click();
    }
    public void selectRecipientGroup(String group){
        String recipientGroup = "//div[@id='searchResultsToolbar']//div[@class='uiButton-label'][contains(text(),'";
        switch (group) {
            case "To":
                $(By.xpath(recipientGroup + "To')]")).click();
                break;
            case "Cc":
                $(By.xpath(recipientGroup + "Cc')]")).click();
                break;
            case "Bcc":
                $(By.xpath(recipientGroup + "Bcc')]")).click();
                break;
        }
    }
}
