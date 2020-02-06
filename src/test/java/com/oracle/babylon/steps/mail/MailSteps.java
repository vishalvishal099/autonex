package com.oracle.babylon.steps.mail;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.DraftPage;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.pages.Mail.ViewMail;
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
    private DraftPage draft = new DraftPage();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private EditPreferencesPage editPreferencesPage = new EditPreferencesPage();
    private ViewMail viewMail = new ViewMail();
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
    @When("^user sends saved mail to \"([^\"]*)\"$")
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
        navigator.loginBro(inboxPage, user2, project, page -> {
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

    @Given("{string} with {string} have a mail with {string} in drafts")
    public void haveAMailWithInDrafts(String user, String project, String mailAttributes) {
        navigator.loginBro(composeMail, user, project, page -> {
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
        navigator.loginBro(composeMail, user, project, page -> {
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
        navigator.loginBro(composeMail, user,project, page -> {
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

    @Then("user edits the email from draft and attaches {string} document and sends saved mail to {string}")
    public void userEditsTheEmailFromDraftAndAttachesDocumentAnSendsSavedMailTo(String document, String user) {
        navigator.on(draft, page -> {
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
        navigator.on(composeMail, ComposeMail::verifyValidationMessage);
        navigator.on(composeMail, ComposeMail::previewMail);
        navigator.on(viewMail, ViewMail::verifyNoError);
    }

    @Then("user edits the email from draft and attaches {string} document from full search and sends to {string}")
    public void userEditsTheEmailFromDraftAndAttachesDocumentFromFullSearchAndSendsTo(String document, String user) {
        navigator.on(draft, page -> {
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
            navigator.loginBro(inboxPage, name,project, page -> {
                page.navigateAndVerifyPage();
                page.searchMailNumber(mailNumber);
                assertThat(page.searchResultCount()).isEqualTo(0);
            });
        }
        String[] namesMailReceived = trueUser.split(",");
        for (String name : namesMailReceived) {
            navigator.loginBro(inboxPage, name,project, page -> {
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
            page.composeMail(sender, mailAttributes);
            page.fillTo(reciever);
            mailNumber = page.send();
        });
    }

    @Then("verify {string} has received mail with {string}")
    public void verifyHasReceivedMailWith(String reciever, String mailAttributes) {
        navigator.loginAsUser(inboxPage, reciever, page -> {
            page.navigateAndVerifyPage();
            page.searchMailNumber(mailNumber);
            assertThat(page.searchResultCount()).isGreaterThan(0);
            assertThat(page.getMailNumber()).isEqualTo(mailNumber);
            page.verifyMailDetails(mailAttributes);
        });
    }
}

