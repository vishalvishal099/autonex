package com.oracle.babylon.steps.mail;

import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Setup.EditPreferencesPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class that contains code to convert test scenarios of mail to java code
 * Author : vsinghsi
 */
public class MailSteps {
    InboxPage inboxPage = new InboxPage();
    Navigator navigator = new Navigator();
    ComposeMail composeMail = new ComposeMail();
    static String mailNumber;

    /**
     * code to search the mail in the inbox
     * @param user
     * @param mailNumber
     */
    @When("^\"([^\"]*)\" search mail \"([^\"]*)\" in inbox$")
    public void searchMailInInbox(String user, String mailNumber) {
        navigator.loginAsUser(inboxPage, user, page -> {
            page.selectMenuSubMenu();
            page.searchMailNumber(mailNumber);
        });
    }

    /**
     * Code to verify if the mail is in the mail box
     * @param mailNumber
     * @throws Throwable
     */
    @Then("^user should see the mail \"([^\"]*)\" in inbox$")
    public void userShouldSeeTheMailInInbox(String mailNumber) throws Throwable {
        navigator.on(inboxPage, page -> {
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);
        });
    }

    /**
     * Code to compose a sample mail
     * @param user
     * @param data
     */
    @Given("^\"([^\"]*)\" compose mail with \"([^\"]*)\"$")
    public void composeMailWith(String user, String data) {
        navigator.loginAsUser(composeMail, user, page -> {
            page.selectMenuSubMenu();
           page.composeMail(user,data);
        });
    }

    /**
     * Code to send mail to a user
     * @param user2
     * @throws Throwable
     */
    @When("^sent mail to \"([^\"]*)\"$")
    public void sentMailTo(String user2) throws Throwable {
        navigator.on(composeMail, page -> {
            page.fillTo(user2);
            mailNumber = page.send();
        });
    }

    /**
     * Verify if the user has received the mail
     * @param user2
     * @throws Throwable
     */
    @Then("^verify \"([^\"]*)\" has received mail$")
    public void verifyHasReceivedMail(String user2) throws Throwable {
        navigator.loginAsUser(inboxPage,user2, page ->{
            page.selectMenuSubMenu();
            page.searchMailNumber(mailNumber);
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);
        });
    }

    @When("Login and add a mail attribute \"([^\"]*)\"")
    public void loginAndAddAMailAttribute(String attributeNumber) throws IOException, ParseException {
        ConfigFileReader configFileReader = new ConfigFileReader();
        //The data is taken from userData.json file and we search for the project in admin tool
        DataSetup dataSetup = new DataSetup();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");
        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");
        String projectName = projectMap.get("projectname");
        //Locking in the field labels
        Navigator navigator = new Navigator();
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectName);
        EditPreferencesPage editPreferencesPage = new EditPreferencesPage();
       WebDriver driver = editPreferencesPage.navigateEditPreferences();
       String attributeValue = editPreferencesPage.createNewMailAttribute(attributeNumber, projectName);
        new DataStore().storeAttributeInfo(attributeNumber, attributeValue);
    }
}
