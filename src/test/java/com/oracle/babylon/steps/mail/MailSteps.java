package com.oracle.babylon.steps.mail;

import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.worldHelper.helper.Navigator;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class MailSteps {
    InboxPage inboxPage = new InboxPage();
    Navigator navigator = new Navigator();

    @When("^\"([^\"]*)\" search mail \"([^\"]*)\" in inbox$")
    public void searchMailInInbox(String user, String mailNumber) throws Throwable {
        navigator.visit(inboxPage, user, page -> {
            page.selectMenuSubMenu();
            page.searchMailNumber(mailNumber);
        });
    }

    @Then("^user should see the mail$")
    public void userShouldSeeTheMail() throws Throwable {
        navigator.on(inboxPage, page -> {
            assertThat(page.searchResultCount()).isGreaterThan(0);
        });
    }
}
