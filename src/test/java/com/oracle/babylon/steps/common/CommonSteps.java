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
    String userDataPath = configFileReader.getUserDataJsonFilePath();
    String mailDataPath = configFileReader.getMailDataJsonFilePath();
    String docDataPath = configFileReader.getDocumentDataJsonFilePath();

    @When("Login and set the web services api checkbox for user {string} and project {string}")
    public void enableWebServicesAPI(String userid, String projectIdentifier)  {
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(userDataPath);
        navigator.loginAsUser(adminTools, page -> {

            //Project ID Info
            Map<String, String> userMap = mapOfMap.get(userid);
            String projectIdKey = "project_id" + projectIdentifier.charAt(projectIdentifier.length()-1);
            String projectId = userMap.get(projectIdKey);
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
            attributeList = new String[]{attributeNumber.toLowerCase()};
            dataSetup.convertMapAndWrite(attributeList, attributeMap, docDataPath);
        } else{
          /**  attributeList = new String[]{"mailattribute", attributeNumber.toLowerCase()};
            dataSetup.convertMapAndWrite(attributeList, attributeMap.get(attributeNumber), mailDataPath);*/
        }
    }
}
