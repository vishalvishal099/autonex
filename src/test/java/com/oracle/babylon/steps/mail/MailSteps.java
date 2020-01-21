package com.oracle.babylon.steps.mail;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.pages.Setup.EditPreferencesPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class that contains code to convert test scenarios of mail to java code
 * Author : vsinghsi
 */
public class MailSteps {
    private InboxPage inboxPage = new InboxPage();
    private Navigator navigator = new Navigator();
    private ComposeMail composeMail = new ComposeMail();
    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private EditPreferencesPage editPreferencesPage = new EditPreferencesPage();
    static String mailNumber;
    String filePath = configFileReader.getUserDataJsonFilePath();

    /**
     * code to search the mail in the inbox
     *
     * @param user
     * @param mailNumber
     */
    @When("^\"([^\"]*)\" search mail \"([^\"]*)\" in inbox$")
    public void searchMailInInbox(String user, String mailNumber) {
        navigator.loginAsUser(inboxPage, user, page -> {
            page.navigateAndVerifyPage();
            page.searchMailNumber(mailNumber);

        });
    }

    /**
     * Code to verify if the mail is in the mail box
     *
     * @param mailNumber
     * @throws Throwable
     */
    @Then("^user should see the mail \"([^\"]*)\" in inbox$")
    public void userShouldSeeTheMailInInbox(String mailNumber) {
        navigator.on(inboxPage, page -> {
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);
        });
    }

    /**
     * Code to compose a sample mail
     *
     * @param user
     * @param data
     */
    @Given("^\"([^\"]*)\" compose mail with \"([^\"]*)\"$")
    public void composeMailWith(String user, String data) {
        navigator.loginAsUser(composeMail, user, page -> {
            page.navigateAndVerifyPage();
            page.composeMail(user, data);
        });
    }

    /**
     * Code to send mail to a user
     *
     * @param user2
     * @throws Throwable
     */
    @When("^sent mail to \"([^\"]*)\"$")
    public void sentMailTo(String user2) {
        navigator.on(composeMail, page -> {
            page.fillTo(user2);
            mailNumber = page.send();
        });
    }

    /**
     * Verify if the user has received the mail
     *
     * @param user2
     * @throws Throwable
     */
    @Then("^verify \"([^\"]*)\" has received mail$")
    public void verifyHasReceivedMail(String user2) {
        navigator.loginAsUser(inboxPage, user2, page -> {
            page.navigateAndVerifyPage();
            page.searchMailNumber(mailNumber);
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);

        });
    }

    @When("Login for user \"([^\"]*)\" and add a mail attribute \"([^\"]*)\"")
    public void loginAndAddAMailAttribute(String userId, final String attributeNumber) {
        String projectKey = "project" + userId.charAt(userId.length()-1);
       Map<String, String> map = dataSetup.loadJsonDataToMap(filePath).get(projectKey);
        navigator.loginAsUser(editPreferencesPage, userId, filePath, page -> {
            page.navigateAndVerifyPage();
            String attributeValue = page.createNewMailAttribute(attributeNumber, map.get("projectname"));
            new DataStore().storeAttributeInfo(attributeNumber, attributeValue);
        });

    }
}
