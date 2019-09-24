package com.oracle.babylon.steps;

import com.oracle.babylon.Utils.helper.Navigator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class LoginSteps {
    Navigator navigator = new Navigator();

    @Given("^\"([^\"]*)\" login with correct username and password$")
    public void loginWithCorrectUsernameAndPassword(String userdetails) throws Throwable {
        navigator.visit(navigator, userdetails, page -> {
        });
    }

    @Then("^user should logged into aconex$")
    public void userShouldLoggedIntoAconex() throws Throwable {
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
}
