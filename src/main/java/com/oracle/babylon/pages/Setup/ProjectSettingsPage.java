package com.oracle.babylon.pages.Setup;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class ProjectSettingsPage {

    private By lockDocFieldsBtn = By.xpath("//button[@id='btnLockDocumentFields']");
    private By unlockDocFieldsBtn = By.id("btnUnlockDocumentFields");
    private By lockFieldOkBtn = By.xpath("//div[@id='lockFieldsPanel']//button[@id='btnlockFieldsPanel_ok']");
    private By saveChangesBtn = By.xpath("//button[@title='Save all changes before continuing']");
    private By attributeTextArea = By.name("newNames");
    private By addBtn = By.id("btnAdd");
    private By saveBtn = By.id("btnSave");
    private By projectSettingsLabel = By.xpath("//h1[text()='Project Settings']");



    /**
     * Function to lock the document field labels for a project
     */
    public void lockDocFieldsBtn() {
        CommonMethods commonMethods = new CommonMethods();
        WebDriver driver = WebDriverRunner.getWebDriver();
        commonMethods.switchToFrame(driver, By.xpath("//iframe[@class='settingsIframe']"));
        String result = commonMethods.returnAttributeValue(lockDocFieldsBtn, "title");
        if (!result.equals("Cannot lock field names - already locked")) {
            $(lockDocFieldsBtn).click();
            $(lockFieldOkBtn).click();
        } else {
            System.out.println("Cannot lock the button as it is already locked");
        }
    }

    /**
     * Function to unlock the field labels
     */
    public void unlockDocFieldsBtn() {
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.switchToFrame(WebDriverRunner.getWebDriver(), "settingsIframe");
        if ($(unlockDocFieldsBtn).isDisplayed()) {
            $(unlockDocFieldsBtn).click();
        }
    }

    /**
     * Function to navigate to the project settings page under Setup
     * @param driver
     */
    public WebDriver navigateToProjectSettings(WebDriver driver){
        CommonMethods commonMethods = new CommonMethods();
        return commonMethods.selectSubMenu(driver, "Setup", "Project Settings", "frameMain");
    }

    /**
     * Function to navigate to the Documents setting page and lock the fields button
     */
    public void lockFieldsInDocuments(){
        WebDriver driver = WebDriverRunner.getWebDriver();
        ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage();
        CommonMethods commonMethods = new CommonMethods();
        driver = projectSettingsPage.navigateToProjectSettings(driver);
        commonMethods.switchToFrame(driver, "frameMain");
        commonMethods.clickLinkToChange( projectSettingsLabel, "Documents");
        lockDocFieldsBtn();
    }

    /**
     * Function to click on any link to configure any label
     * @param labelToEdit
     */
    public void clickLabelToEdit(String labelToEdit){
        By editLabelLink = By.xpath("//td[text()='" + labelToEdit + "']//..//td[6]");
        $(editLabelLink).click();
        $(saveChangesBtn).click();

    }

    /**
     * Function to add a attribute for documents
     * @return attribute name
     */
    public String addAttribute(){
        Faker faker = new Faker();
        String attribute = faker.commerce().department();
        $(attributeTextArea).sendKeys(attribute);
        $(addBtn).click();
        $(saveBtn).click();
        return attribute;
    }

    /**
     * Function to verify if the button lock fields in enabled
     * @return enabled status
     */
    public boolean isLockFieldsBtnEnabled(){
        CommonMethods commonMethods = new CommonMethods();
        String result = commonMethods.returnAttributeValue(lockDocFieldsBtn, "title");
        if (result.equals("Cannot lock field names - already locked")) {
            return false;
        } else {
            return true;
        }
    }
}
