package com.oracle.babylon.steps.mail;

import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.Utils.helper.Navigator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class MailSteps {
    InboxPage inboxPage = new InboxPage();
    Navigator navigator = new Navigator();
    ComposeMail composeMail = new ComposeMail();
    static String mailNumber;

    @When("^\"([^\"]*)\" search mail \"([^\"]*)\" in inbox$")
    public void searchMailInInbox(String user, String mailNumber) {
        navigator.visit(inboxPage, user, page -> {
            page.selectMenuSubMenu();
            page.searchMailNumber(mailNumber);
        });
    }

    @Then("^user should see the mail \"([^\"]*)\" in inbox$")
    public void userShouldSeeTheMailInInbox(String mailNumber) throws Throwable {
        navigator.on(inboxPage, page -> {
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);
        });
    }

    @Given("^\"([^\"]*)\" compose mail with \"([^\"]*)\"$")
    public void composeMailWith(String user, String data) {
        navigator.visit(composeMail, user, page -> {
            page.selectMenuSubMenu();
           page.composeMail(user,data);
        });
    }

    @When("^sent mail to \"([^\"]*)\"$")
    public void sentMailTo(String user2) throws Throwable {
        navigator.on(composeMail, page -> {
            page.fillTo(user2);
            mailNumber =page.send();
        });
    }

    @Then("^verify \"([^\"]*)\" has received mail$")
    public void verifyHasReceivedMail(String user2) throws Throwable {
        navigator.visit(inboxPage,user2,page ->{
            page.selectMenuSubMenu();
            page.searchMailNumber(mailNumber);
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);
        });
    }

}
