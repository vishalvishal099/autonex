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
        commonMethods.switchToFrame(WebDriverRunner.getWebDriver(), "frameMain");

        //Checking if we need to set each text box value
        if(groupNameVar!=null){
            fillGroupName(groupNameVar);
        }
        if(familyNameVar!=null){
            fillFamilyName(familyNameVar);
        }
        if(organizatioNameVar!=null){
            fillOrganizationName(organizatioNameVar);
        }
        if(jobTitleVar!=null){
            fillJobTitle(jobTitleVar);
        }
        if(divisionVar!=null){
            fillDivision(divisionVar);
        }
        searchBtnClick();

    }

    /**
     * Method to enter the group name in its respective text box
     * @param name
     */
    public void fillGroupName(String name){
        $(groupName).sendKeys(name);
    }

    /**
     * Method to enter the family name in its respective text box
     * @param name
     */
    public void fillFamilyName(String name){
        $(familyName).sendKeys(name);
    }

    /**
     * Method to enter the organization name in its respective text box
     * @param orgName
     */
    public void fillOrganizationName(String orgName){
        $(organizationName).sendKeys(orgName);
    }

    /**
     * Method to enter the division name in its respective text box
     * @param divisionName
     */
    public void fillDivision(String divisionName){
        $(division).sendKeys(divisionName);
    }

    /**i
     * Method to enter the title n its respective text box
     * @param title
     */
    public void fillJobTitle(String title){
        $(jobTitle).sendKeys(title);
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
     * Method to search the directory when group name is provided as input
     * @param groupName
     */
    public void searchGroupName(String groupName){
        fillGroupName(groupName);
        searchBtnClick();
    }

    /**
     * Method to search the directory when family name is provided as input
     * @param familyName
     */
    public void searchFamilyName(String familyName){
        fillFamilyName(familyName);
        searchBtnClick();
    }

    /**
     * Method to search the directory when organization name is provided as input
     * @param organizationName
     */
    public void searchOrganizationName(String organizationName){
        fillOrganizationName(organizationName);
        searchBtnClick();
    }

    /**
     * Method to search the directory when job title is provided as input
     * @param jobTitle
     */
    public void searchJobTitle(String jobTitle){
        fillJobTitle(jobTitle);
        searchBtnClick();

    }

    /**
     * Method to search the directory when division is provided as input
     * @param divisionName
     */
    public void searchDivision(String divisionName){
        fillDivision(divisionName);
        searchBtnClick();
    }

    /**
     * Method to obtain group name and family name from full name provided
     * @param fullName
     */
    public void searchName(String fullName){
        String groupName = fullName.split(" ")[0];
        String familyName = fullName.split(" ")[1];
        fillGroupName(groupName);
        fillFamilyName(familyName);
        searchBtnClick();
    }

    /**
     * Method to click on the OK Button
      */
    public void clickOkBtn(){
        $(okBtn).click();
    }


}
