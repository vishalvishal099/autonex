package com.oracle.babylon.pages.Setup;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ProjectSettingsPage extends Navigator{

    private By lockDocFieldsBtn = By.xpath("//button[@id='btnLockDocumentFields']");
    private By unlockDocFieldsBtn = By.id("btnUnlockDocumentFields");
    private By lockFieldOkBtn = By.xpath("//div[@id='lockFieldsPanel']//button[@id='btnlockFieldsPanel_ok']");
    private By saveChangesBtn = By.xpath("//button[@title='Save all changes before continuing']");
    private By attributeTextArea = By.name("newNames");
    private By addBtn = By.id("btnAdd");
    private By saveBtn = By.id("btnSave");
    private By projectSettingsLabel = By.xpath("//h1[text()='Project Settings']");
    private By pageTitle = By.xpath("//h1[contains(text(),'Project Settings')]");
    private By documentSettings = By.xpath("//span[contains(text(),'Documents')]");
    private By documentFields = By.xpath("//div[contains(text(),'Document Fields')]");

    /**
     * Function to lock the document field labels for a project
     */
    public void lockDocFieldsBtn() {

        this.driver = WebDriverRunner.getWebDriver();
        commonMethods.switchToFrame(driver, By.xpath("//iframe[@id='project-settings-page']"));
        String result = commonMethods.returnElementAttributeValue(lockDocFieldsBtn, "title");
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
        commonMethods.switchToFrame(WebDriverRunner.getWebDriver(), "settingsIframe");
        if ($(unlockDocFieldsBtn).isDisplayed()) {
            $(unlockDocFieldsBtn).click();
        }
    }

    /**
     * Function to navigate to the project settings page under Setup
     */
    public void navigateAndVerifyPage(){
        getMenuSubmenu( "Setup", "Project Settings");
        Assert.assertTrue(verifyPageTitle(pageTitle));
    }

    /**
     * Function to navigate to the Documents setting page and lock the fields button
     */
    public void lockFieldsInDocuments(){
        this.driver = WebDriverRunner.getWebDriver();
        navigateAndVerifyPage();
        commonMethods.switchToFrame(this.driver, "frameMain");

        commonMethods.clickLinkToChange( projectSettingsLabel, documentSettings);
        commonMethods.clickLinkToChange(projectSettingsLabel, documentFields );
        lockDocFieldsBtn();
    }

    /**
     * Function to click on any link to configure any label
     * @param labelToEdit
     */
    public void clickLabelToEdit(String labelToEdit){
        By editLabelLink = By.xpath("//td[contains(text(),'" + labelToEdit + "')]//..//td[6]//a");
        $(editLabelLink).click();
        $(saveChangesBtn).click();

    }

    /**
     * Function to add a attribute for documents
     * @return attribute name
     */
    public String createNewDocumentAttribute(){
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
        String result = commonMethods.returnElementAttributeValue(lockDocFieldsBtn, "title");
        if (result.equals("Cannot lock field names - already locked")) {
            return false;
        } else {
            return true;
        }
    }
}
