package com.oracle.babylon.steps;

import com.oracle.babylon.worldHelper.Setup.DataStore.DataTableConverter;
import com.oracle.babylon.worldHelper.helper.Navigator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

public class LoginSteps {
    Navigator navigator =new Navigator();

    @Given("^User Data \"([^\"]*)\"$")
    public void userData(String name, DataTable dataTable) throws Throwable {
        new DataTableConverter().addUser(name, dataTable);


    }

    @Given("^\"([^\"]*)\" login with correct username and password$")
    public void loginWithCorrectUsernameAndPassword(String userdetails) throws Throwable {
        navigator.visit(navigator, userdetails, page -> {
        });
    }

    @Then("^user should logged into aconex$")
    public void userShouldLoggedIntoAconex() throws Throwable {
        navigator.on(navigator,page -> {
            page.verifyUserPresent();
        });
    }


    @Given("^\"([^\"]*)\" login with incorrect username and password$")
    public void loginWithIncorrectUsernameAndPassword(String userdetails) throws Throwable {
        navigator.as(userdetails);
    }

    @Then("^user should not logged into aconex$")
    public void userShouldNotLoggedIntoAconex() throws Throwable {
        navigator.on(navigator,page -> {
            page.verifyLoginFailed();
        });
    }
}
