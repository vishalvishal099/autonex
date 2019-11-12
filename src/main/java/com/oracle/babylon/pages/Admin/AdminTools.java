package com.oracle.babylon.pages.Admin;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that contains functions to manage configurations
 * Author : susgopal
 */
public class AdminTools {

    //Web element initialization
    private By adminToolsLabel = By.xpath("//h1[text()='Aconex Admin Tools']");
    private By configureComponentsForProjectLink = By.xpath("//a[text()='Configure Components for Project']");
    private By configureAccessibleComponentsLabel = By.xpath("//h1[contains(text(),'Configure Accessible Components for')]");
    private By projectIdTextBox = By.name("SELECTED_PROJECT_ID");
    private By lookupBtn = By.xpath("//button[@title='Search for this Project']");
    private By saveBtn =  By.xpath("//button[@title='Commit the feature settings']");
    private By successMsg = By.xpath("//div[text()='Success: Feature settings successfully updated.']");

    /**
     * Function to select the link we want to update in the project settings page
     * @param by
     */
    public void selectOptionToConfigure(By by){
        $(by).click();
    }

    /**
     * Function to configure the components for a project
     * @param setElements elements that need to be configured
     * @param projectId project id to be configured
     */
    public void configureComponentsForProject(Set<String> setElements, String projectId){
        WebDriver driver = WebDriverRunner.getWebDriver();
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.switchToFrame(driver, "frameMain");
        $(adminToolsLabel).isDisplayed();
        selectOptionToConfigure(configureComponentsForProjectLink);
        $(configureAccessibleComponentsLabel).isDisplayed();
        $(projectIdTextBox).sendKeys(projectId);
        $(lookupBtn).click();

        $(configureAccessibleComponentsLabel).isDisplayed();
        for(String optionToConfigure : setElements){
            By attributeToSelect = By.xpath("//td[contains(text(), '" + optionToConfigure + "')]//input");
            if(!commonMethods.isAttributePresent(attributeToSelect, "checked") && !commonMethods.isAttributePresent(attributeToSelect, "disabled")){
                $(attributeToSelect).click();
            }
        }
        $(saveBtn).click();
        driver.switchTo().defaultContent();
    }

    /**
     * Enabling web services api for a project
     * @param projectId project id that needs to be configured
     */
    public void enableWebServicesAPI(String projectId){

        Set<String> featuresToEnable = new HashSet<>();
        featuresToEnable.add("Web Services API");
        configureComponentsForProject(featuresToEnable, projectId);
    }

    /**
     * Navigate to the tools page
     * @param driver
     * @return
     */
    public WebDriver navigateToTools(WebDriver driver) {
        CommonMethods commonMethods = new CommonMethods();
        return commonMethods.selectSubMenuAdmin(driver, "Setup", "Tools", "frameMain");
    }


    /**
     * Function to verify if success message
     * @return
     */
    public boolean isFeatureSettingsSaved(){
        return $(successMsg).isDisplayed();
    }



}
