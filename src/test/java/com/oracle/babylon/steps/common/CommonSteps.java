package com.oracle.babylon.steps.common;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminTools;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

public class CommonSteps {

    private ConfigFileReader configFileReader = new ConfigFileReader();
    private DataSetup dataSetup = new DataSetup();
    private AdminTools adminTools = new AdminTools();
    private DataStore dataStore = new DataStore();
    Navigator navigator = new Navigator();
    String filepath = configFileReader.getUserDataJsonFilePath();

    @When("Login and set the web services api checkbox for project \"([^\"]*)\"")
    public void enableWebServicesAPI(String projectIdentifier)  {
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(filepath);
        navigator.loginAsUser(adminTools, page -> {

            //Project ID Info
            Map<String, String> projectMap = mapOfMap.get(projectIdentifier);
            String projectId = projectMap.get("projectId");
            page.navigateAndVerifyPage();
            page.enableWebServicesAPI(projectId);
        });
    }

    @Then("verify if feature changes save is successful")
    public void verifyIfFeatureChangesSaveIsSuccessful() {
        adminTools.isFeatureSettingsSaved();
    }

    @Then("Write \"([^\"]*)\" for \"([^\"]*)\" in userData.json")
    public void writeAttributeIntoUserDataJson(String attributeNumber, String superkey) {
        Map<String, String> attributeMap = dataStore.getAttributeHashMap();
        String[] attributeList = null;
        if(superkey.equals("Document")){
            attributeList = new String[]{"docattribute", attributeNumber.toLowerCase()};
        } else{
            attributeList = new String[]{"mailattribute", attributeNumber.toLowerCase()};
        }
        dataSetup.writeIntoJson(attributeList, attributeMap.get(attributeNumber), filepath);

    }
}
