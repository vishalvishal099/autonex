package com.oracle.babylon.steps;

import com.oracle.babylon.Utils.helper.JIRAOperations;
import com.oracle.babylon.Utils.helper.Navigator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.IOException;

public class LoginSteps {
    private Navigator navigator = new Navigator();
    private JIRAOperations jiraOperations = new JIRAOperations();

    @Given("^\"([^\"]*)\" login with correct username and password$")
    public void loginWithCorrectUsernameAndPassword(String userdetails) throws Throwable {
        navigator.loginAsUser(navigator, userdetails, page -> {
        });
    }

    @Then("^user should logged into aconex$")
    public void userShouldLoggedIntoAconex() {
        navigator.on(navigator, page -> {
                page.verifyUserPresent();

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

    @Given("{string} retrieve details")
    public void retrieveDetails(String tablename) throws IOException {

        jiraOperations.getJiraTicket(tablename);
        //jiraOperations.addComment(tablename);
    }

    @Then("views the home page")
    public void viewsTheHomePage(){
            navigator.verifyUserPresent();
    }
}
