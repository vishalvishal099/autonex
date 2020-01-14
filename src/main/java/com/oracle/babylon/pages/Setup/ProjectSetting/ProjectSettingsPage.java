package com.oracle.babylon.pages.Setup.ProjectSetting;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    /**
     * Function to navigate to the project settings page under Setup
     */
    public void navigateAndVerifyPage(){
        getMenuSubmenu( "Setup", "Project Settings");
        verifyPageTitle("Project Settings");
    }

    /**
     * Function to lock the document field labels for a project
     */
    public void lockDocFieldsBtn() {
        commonMethods.waitForElementExplicitly(configFileReader.getImplicitlyWait()*500);
        driver = WebDriverRunner.getWebDriver();
        commonMethods.switchToFrame(driver, By.xpath("//iframe[@class='settingsIframe']"));
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
     * Function to navigate to the Documents setting page and lock the fields button
     */
    public void lockFieldsInDocuments() {
        navigateAndVerifyPage();
        this.driver = WebDriverRunner.getWebDriver();
        commonMethods.switchToFrame(this.driver, "frameMain");
        commonMethods.clickHyperLinkToChange( projectSettingsLabel, "Documents");
        lockDocFieldsBtn();
    }

    /**
     * Function to click on any link to configure any label
     * @param labelToEdit
     */
    public void clickLabelToEdit(String labelToEdit) {
        commonMethods.switchToFrame(this.driver, "frameMain");
        commonMethods.clickHyperLinkToChange( projectSettingsLabel, "Documents");
        commonMethods.waitForElementExplicitly(configFileReader.getImplicitlyWait()*500);
        commonMethods.switchToFrame(driver, By.xpath("//iframe[@class='settingsIframe']"));
        By editLabelLink = By.xpath("//td[contains(text(),'" + labelToEdit + "')]//..//td[6]//a");
        $(editLabelLink).scrollTo();
        $(editLabelLink).click();
        $(saveChangesBtn).click();

    }

    /**
     * Function to add a attribute for documents
     * @return attribute name
     */
    public String createNewDocumentAttribute() {
        Faker faker = new Faker();
        String attribute = faker.address().city();
        $(attributeTextArea).sendKeys(attribute);
        commonMethods.waitForElementExplicitly(2000);
        $(addBtn).click();
        commonMethods.waitForElementExplicitly(2000);
        $(saveBtn).click();
        return attribute;
    }

    /**
     * Function to verify if the button lock fields in enabled
     * @return enabled status
     */
    public boolean isLockFieldsBtnEnabled(){
        commonMethods.waitForElement(driver, lockDocFieldsBtn);
        String result = commonMethods.returnElementAttributeValue(lockDocFieldsBtn, "title");
        if (result.equals("Cannot lock field names - already locked")) {
            return false;
        } else {
            return true;
        }
    }
}
