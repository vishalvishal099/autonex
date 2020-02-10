package com.oracle.babylon.steps.mail;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.pages.Setup.EditPreferencesPage;
import cucumber.api.java.en.And;
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
    static String draftMailNumber;
    String userDataFile = configFileReader.getUserDataJsonFilePath();
    protected Map<String, Map<String, String>> jsonMapOfMap = null;
    protected Map<String, String> userMap = null;
    String userFilePath = configFileReader.getUserDataJsonFilePath();

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
    @Then("^verify \"([^\"]*)\" with \"([^\"]*)\" has received mail$")
    public void verifyHasReceivedMail(String user2, String project) {
        navigator.loginAsUser(inboxPage, user2, project, page -> {
            page.navigateAndVerifyPage();
            page.searchMailNumber(mailNumber);
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);

        });
    }

    @When("Login for user \"([^\"]*)\" and add a mail attribute \"([^\"]*)\"")
    public void loginAndAddAMailAttribute(String userId, final String attributeNumber) {

        String projectKey = "project" + userId.charAt(userId.length() - 1);
        Map<String, String> map = dataSetup.loadJsonDataToMap(userDataFile).get(projectKey);
        navigator.loginAsUser(editPreferencesPage, userId, userDataFile, page -> {
            page.navigateAndVerifyPage();
            String attributeValue = page.createNewMailAttribute(attributeNumber, map.get("projectname"));
            new DataStore().storeAttributeInfo(attributeNumber, attributeValue);
        });

    }
    
//Please rename step methods to better method names
    @And("sends mail to user {string} in tolist and user {string} in cclist")
    public void sendsMailToInTolistAndInCclist(String userId1, String userId2) {

    @Given("{string} with {string} have a mail with {string} in drafts")
    public void haveAMailWithInDrafts(String user, String project, String mailAttributes) {
        navigator.loginAsUser(composeMail, user, project, page -> {
            page.navigateAndVerifyPage();
            page.composeMail(user, mailAttributes);
            draftMailNumber = page.userDefinedMailNumber();
            page.saveToDraft();
        });
    }

    @Then("user edits the email from draft and attaches {string} document")
    public void userEditsTheEmailFromDraftAndAttachesDocument(String document) {
        navigator.on(draft, page -> {
            page.navigateAndVerifyPage();
            page.selectDraftMail(draftMailNumber);
        });
        navigator.on(viewMail, ViewMail::editMail);
        navigator.on(composeMail, page -> {
            page.attachDocument(document);
//            page.verifyValidationMessage();
        });
    }

    @Then("user edits the email from draft and attaches {string} document from full search")
    public void userEditsTheEmailFromDraftAndAttachesDocumentFromFullSearch(String document) {
        navigator.on(draft, page -> {
            page.navigateAndVerifyPage();
            page.selectDraftMail(draftMailNumber);
        });
        navigator.on(viewMail, ViewMail::editMail);
        navigator.on(composeMail, page -> {
            page.attachDocumentUsingFullSearch(document);
        });
    }

    @Then("user edits the mail and removes {string} from {string}")
    public void userEditsTheMailAndRemovesFrom(String user, String group) {
        navigator.on(draft, page -> {
            page.navigateAndVerifyPage();
            page.selectDraftMail(draftMailNumber);
        });
        navigator.on(viewMail, ViewMail::editMail);
        navigator.on(composeMail, page -> {
            page.removeUserFromMailingList(group, user);
        });
    }

    @Then("user sends the mail")
    public void userSendsTheMail() {
        navigator.on(composeMail, page -> {
            //page.fillTo(userId1);
            page.fillTo(fullname);
            page.fillCc(fullname1);
            mailNumber = page.send();
        });
    }

    @And("user removes {string} from {string}")
    public void userRemovesFrom(String user, String group) {
        navigator.on(composeMail, page -> {
            page.removeUserFromMailingList(group, user);
        });
    }

    @Then("user attaches {string} in the mail and sends mail")
    public void userAttachesInTheMail(String document) {
        navigator.on(composeMail, page -> {
            page.attachDocument(document);
        });
        navigator.on(composeMail, page -> {
            mailNumber = page.send();
        });
    }

    @Then("verify {string} has not received mail")
    public void verifyHasNotReceivedMail(String user) {
        String[] namesMailNotReceived = user.split(",");
        for (String name : namesMailNotReceived) {
            navigator.loginAsUser(inboxPage, name, page -> {
                page.navigateAndVerifyPage();
                page.searchMailNumber(mailNumber);
                assertThat(page.searchResultCount()).isEqualTo(0);
            });
        }
    }

    @Given("{string} with {string} previews a blank mail")
    public void previewsABlankMail(String user, String project) {
        navigator.loginAsUser(composeMail, user, project, page -> {
            page.navigateAndVerifyPage();
            draftMailNumber = page.userDefinedMailNumber();
            page.previewMail();
        });
    }

    @Then("mail will be present in the draft")
    public void mailWillBePresentInTheDraft() {
        navigator.on(draft, page -> {
            page.navigateAndVerifyPage();
            page.verifyMailInDraft(draftMailNumber);
        });
    }

    @Given("{string} with {string} previews mail with {string}")
    public void previewsMailWith(String user,String project, String mailAttribute) {
        navigator.loginAsUser(composeMail, user,project, page -> {
            page.navigateAndVerifyPage();
            page.composeMail(user, mailAttribute);
            draftMailNumber = page.userDefinedMailNumber();
            page.previewMail();
        });
    }

    @And("user verify validation message for not file in document")
    public void userVerifyValidationMessageForNotFileInDocument() {
        navigator.on(composeMail, ComposeMail::verifyValidationMessage);
    }

    @And("verify document details on inbox page for {string}")
    public void verifyDocumentDetailsOnInboxPageFor(String document) {
        navigator.on(inboxPage, page -> {
            page.openEmail();
        });
        navigator.on(viewMail, page -> {
            page.verifyDocumentDetails(document);
        });
    }

    @And("user mark mail as unread")
    public void userMarkMailAsUnread() {
        navigator.on(inboxPage, InboxPage::openEmail);
        navigator.on(viewMail, ViewMail::markAsUnread);
    }

    @Then("verify user {string} for {string} option and user {string} for {string} option")
    public void verifyUserForOptionAndUserForOption(String userId1, String to, String userId2, String cc) {
        //There has been a change in the loginAsUser method. Please take the latest pull and check. Make the necessary changes
        navigator.loginAsUser(inboxPage,userId1,userDataPath,page-> {
            page.navigateAndVerifyPage();
            page.selectDraftMail(draftMailNumber);
        });
        navigator.on(viewMail, ViewMail::editMail);
        navigator.on(composeMail, page -> {
            page.attachDocument(document);
            jsonMapOfMap = dataSetup.loadJsonDataToMap(userFilePath);
            userMap = jsonMapOfMap.get(user);
            userMap.get("full_name");
            page.addRecipient("To", userMap.get("full_name"));
            mailNumber = page.send();
        });
    }

    @Then("user edits the email attaches {string} document & verify message and no error message on preview")
    public void userEditsTheEmailAttachesDocumentVerifyMessageAndNoErrorMessageOnPreview(String document) {
        navigator.on(draft, page -> {
            page.navigateAndVerifyPage();
            page.selectDraftMail(draftMailNumber);
        });
        navigator.on(viewMail, ViewMail::editMail);
        navigator.on(composeMail, page -> {
            page.attachDocument(document);
//            page.verifyValidationMessage();
        });

        navigator.loginAsUser(inboxPage,userId2,userDataPath,page-> {
            page.navigateAndVerifyPage();
            page.selectDraftMail(draftMailNumber);
        });
        navigator.on(viewMail, ViewMail::editMail);
        navigator.on(composeMail, page -> {
            page.attachDocumentUsingFullSearch(document);
        });
        navigator.on(composeMail, page -> {
            jsonMapOfMap = dataSetup.loadJsonDataToMap(userFilePath);
            userMap = jsonMapOfMap.get(user);
            userMap.get("full_name");
            page.addRecipient("To", userMap.get("full_name"));
            mailNumber = page.send();
        });
    }

    @Then("verify {string} has not received mail and {string} has for {string}")
    public void verifyHasNotReceivedMailAndHas(String falseUser, String trueUser, String project) {
        String[] namesMailNotReceived = falseUser.split(",");
        for (String name : namesMailNotReceived) {
            navigator.loginAsUser(inboxPage, name,project, page -> {
                page.navigateAndVerifyPage();
                page.searchMailNumber(mailNumber);
                assertThat(page.searchResultCount()).isEqualTo(0);
            });
        }
        String[] namesMailReceived = trueUser.split(",");
        for (String name : namesMailReceived) {
            navigator.loginAsUser(inboxPage, name,project, page -> {
                page.navigateAndVerifyPage();
                page.searchMailNumber(mailNumber);
                assertThat(page.searchResultCount()).isGreaterThan(0);
                assertThat(page.getMailNumber()).isEqualTo(mailNumber);
            });
        }
    }

    @Given("{string} creates a mail with {string} and sends it to {string}")
    public void createsAMailWithAndSendsItTo(String sender, String mailAttributes, String reciever) {
        navigator.loginAsUser(composeMail, sender, page -> {
            page.navigateAndVerifyPage();
            page.verifyToAndCcAndAny(userId1,any);
            page.searchMailNumber(mailNumber);
            assertThat(page.getUserName()).contains(page.getRecipientName());
        });

        navigator.loginAsUser(inboxPage,userId2,userDataPath,page-> {
            page.navigateAndVerifyPage();
            page.verifyToAndCcAndAny(userId2,any);
            page.searchMailNumber(mailNumber);
            assertThat(page.getUserName()).contains(page.getRecipientName());
        });
    }

    @Then("verify {string} in preview")
    public void verifyInPreview(String mailAttribute) {
    }
}

