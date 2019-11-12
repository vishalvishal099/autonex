package com.oracle.babylon.pages.Setup;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class EditPreferencesPage {


    private By editPreferencesLabel = By.xpath("//h1[text()='Edit Preferences']");
    private By addLink = By.xpath("//a[text()='Add']");
    private By saveBtn = By.id("btnSave");


    public WebDriver navigateEditPreferences(){
        CommonMethods commonMethods = new CommonMethods();
        return commonMethods.selectSubMenu( WebDriverRunner.getWebDriver(), "Setup", "Preferences", "frameMain");
    }


    /**
     * Function to set the value of the checkbox for Attribute to be made compulsory
     * @param attribute
     */
    public void setAttributeCompulsory(String attribute){

        int number = Integer.parseInt(attribute.substring(attribute.length()-1));
        By attributeChkBox = By.name("PREFERENCE_mailattributes" + number + "compulsory_DISPLAY");
        By attributeCurrentValue = By.name("PREFERENCE_mailattributes" + number + "compulsory_CURRENT_VALUE");
        CommonMethods commonMethods = new CommonMethods();
        String value = commonMethods.returnAttributeValue(attributeCurrentValue, "value");
        if(value!="true"){
            $(attributeChkBox).click();
        }

    }

    /**
     *Function to click the edit button for any label
     */
    public void clickEditBtn(String labelTxt){
        WebDriver driver = WebDriverRunner.getWebDriver();
        WebElement element = driver.findElement(By.xpath("//td[text()='" + labelTxt + "']//.."));
         By editBtn = By.xpath("//button[@title='Edit this item']");
         element.findElement(editBtn).click();

    }

    /**
     * Function to validate if we have navigated to the right page
     * @param attributeNum
     * @param projectName
     */
    public void checkLabelWhenAddingAttribute(String attributeNum, String projectName){
        attributeNum = attributeNum.substring(0, attributeNum.length()-1) + " " + attributeNum.substring(attributeNum.length()-2, attributeNum.length()-1);
        By header = By.xpath("//h1[text()='Select values for " +  attributeNum+ " -Project: " + projectName + "']");
        $(header).isDisplayed();
    }

    /**
     * Function to add attributes to mails
     * @param attributeNumber
     * @return
     */
    public String addMailAttribute(String attributeNumber){
        Faker faker = new Faker();
        String attribute = faker.commerce().department();
        int number = Integer.parseInt(attributeNumber.substring(attributeNumber.length()-1));
        By attributeTxtAreaBox = By.name("PREFERENCE_mailattributes" + number + "list_new");
        $(attributeTxtAreaBox).sendKeys(attribute);
        $(addLink).click();
        $(saveBtn).click();
        return attribute;
    }

    /**
     * Single function to redirect to the relevant page and create a attribute of user's choice
     * @param attributeNumber
     * @param projectName
     * @return
     */
    public String createNewMailAttribute(String attributeNumber, String projectName){
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.clickLinkToChange(editPreferencesLabel, "Project");
        setAttributeCompulsory(attributeNumber);
        String lblTxt = "Select values for " + attributeNumber;
        clickEditBtn(lblTxt);
        checkLabelWhenAddingAttribute(attributeNumber, projectName);
        return addMailAttribute(attributeNumber);
    }
}
