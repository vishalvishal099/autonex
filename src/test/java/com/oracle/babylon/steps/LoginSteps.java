package com.oracle.babylon.steps;

import com.oracle.babylon.Utils.helper.JIRAOperations;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.IOException;
import java.util.Map;

public class LoginSteps {
    private Navigator navigator = new Navigator();
    private JIRAOperations jiraOperations = new JIRAOperations();

    @Given("^\"([^\"]*)\" login with correct username and password$")
    public void loginWithCorrectUsernameAndPassword(String userdetails) throws Throwable {
        navigator.loginAsUser(navigator, userdetails, page -> {
        });
    }

    @Given("^\"([^\"]*)\" login with incorrect username and password$")
    public void loginWithIncorrectUsernameAndPassword(String userdetails) throws Throwable {
        navigator.as(userdetails);
    }

    @Then("^user should not logged into aconex$")
    public void userShouldNotLoggedIntoAconex() throws Throwable {
        navigator.on(navigator, page -> {
            page.verifyLoginFailed();
        });
    }

    @Given("\"([^\"]*)\", retrieve details")
    public void retrieveDetails(String jiraId) throws IOException {
        String issueId = jiraOperations.getJiraId(jiraId);
        int executionId = jiraOperations.returnLatestExecutionId(issueId, "20.1.100");
       // jiraOperations.returnExecutionStatusId(issueId);
       // System.out.println(jiraOperations.updateLatestExecutionStatus(executionId, 1).getBody().asString());

    }

    @Then("views the home page")
    public void viewsTheHomePage(){
        DataSetup dataSetup = new DataSetup();
        ConfigFileReader configFileReader = new ConfigFileReader();
        String userDataPath = configFileReader.getUserDataJsonFilePath();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(userDataPath);
        Map<String, String> userMap = mapOfMap.get("user1");
        String fullname = userMap.get("full_name");
            navigator.verifyUserPresent(fullname);
    }

    @Then("user {string} should logged into aconex")
    public void userShouldLoggedIntoAconex(String userDetails) {
        DataStore dataStore = new DataStore();
       User user = dataStore.getUser(userDetails);
        navigator.on(navigator, page -> {
            page.verifyUserPresent(user.getFullName());

        });
    }
}
