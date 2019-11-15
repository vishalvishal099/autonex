package com.oracle.babylon.steps.common;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminTools;
import com.oracle.babylon.pages.Document.DocumentPage;
import com.oracle.babylon.pages.Setup.ProjectSettingsPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

public class CommonSteps {

    private ConfigFileReader configFileReader = new ConfigFileReader();
    private DataSetup dataSetup = new DataSetup();
    private AdminTools adminTools = new AdminTools();
    private DataStore dataStore = new DataStore();

    @When("Login and set the web services api checkbox")
    public void enableWebServicesAPI() throws IOException, ParseException, InterruptedException {

        //The data is taken from userData.json file and we search for the project in admin tool

        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        //Project ID Info
        Map<String, String> projectMap = mapOfMap.get("project");
        String projectId = projectMap.get("projectId");

        //Selecting the Web Services API checkbox

        WebDriver driver = WebDriverRunner.getWebDriver();
        adminTools.navigateToTools(driver);
        adminTools.enableWebServicesAPI(projectId);



    }

    @Then("verify if feature changes save is successful")
    public void verifyIfFeatureChangesSaveIsSuccessful() {
        adminTools.isFeatureSettingsSaved();
    }

    @Then("Write \"([^\"]*)\" for \"([^\"]*)\" in userData.json")
    public void writeAttributeIntoUserDataJson(String attributeNumber, String superkey) throws IOException, ParseException {
        Map<String, String> attributeMap = dataStore.getAttributeHashMap();
        attributeNumber = attributeNumber.replace(" ", "");
        String[] attributeList = null;
        if(superkey.equals("Documents")){
            attributeList = new String[]{"docattribute", attributeNumber.toLowerCase()};
        } else{
            attributeList = new String[]{"mailattribute", attributeNumber.toLowerCase()};
        }

        dataSetup.writeIntoJson(attributeList, attributeMap.get(attributeNumber), configFileReader.returnUserDataJsonFilePath());

    }
}
