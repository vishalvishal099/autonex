package com.oracle.babylon.pages.Directory;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;

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
    private By jobTitle = By.xpath("//input[@id='POSITION_NAME']");
    private By division = By.xpath("//input[@id='DIVISION_NAME']");
    private By userList = By.xpath("//input[@name='USERS_LIST']");
    private By addUserToBtn = By.xpath("//button[@id='btnAddTo_page']");
    private By okBtn = By.xpath("//button[@id='btnOk']");

    /**
     * Method to input all the text boxes present in the directory search page
     * @param groupNameVar
     * @param familyNameVar
     * @param organizatioNameVar
     * @param jobTitleVar
     * @param divisionVar
     */
    public void fillFieldsAndSearch(String groupNameVar, String familyNameVar, String organizatioNameVar, String jobTitleVar, String divisionVar){
        //Checking if we need to set each text box value
        if(groupNameVar!=null){
            $(groupName).sendKeys(groupNameVar);
        }
        if(familyNameVar!=null){
            $(familyName).sendKeys(familyNameVar);
        }
        if(organizatioNameVar!=null){
            $(organizationName).sendKeys(organizatioNameVar);
        }
        if(jobTitleVar!=null){
            $(jobTitle).sendKeys(jobTitleVar);
        }
        if(divisionVar!=null){
            $(division).sendKeys(divisionVar);
        }
        searchBtnClick();

    }

    /**
     * Method to click the search button
     */
    public void searchBtnClick(){
        $(searchBtn).click();
    }

    /**
     * Method to click o retrieved row's entry
     */
    public void selectEntry(){
        $(userList).click();
    }

    /**
     * Method to click on the To Recipient button
     */
    public void clickToBtn(){
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
     * Method to click on the OK Button
      */
    public void clickOkBtn(){
        $(okBtn).click();
    }


}
