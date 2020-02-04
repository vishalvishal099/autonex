package com.oracle.babylon.steps.setup;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.pages.Setup.EditPreferencesPage;
import com.oracle.babylon.pages.Setup.PreferenceDefaultTab;
import com.oracle.babylon.pages.Setup.PreferenceUserTab;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class that contains code to convert test scenarios of preferences to java code
 * Author : kukumavi
 */
public class PreferencesSteps {
    private InboxPage inboxPage = new InboxPage();
    private Navigator navigator = new Navigator();
    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private PreferenceDefaultTab defaultTab = new PreferenceDefaultTab();
    private PreferenceUserTab userTab = new PreferenceUserTab();
    String filePath = configFileReader.getUserDataJsonFilePath();

    @Given("aconexadmin logs in to default preference page")
    public void logsInToDefaultPreferencePage() {
        navigator.loginAsUser(defaultTab, PreferenceDefaultTab::navigateAndVerifyPage);
    }

    @Then("user selects {string} with use default setting checked")
    public void userSelectsWithUseDefaultChecked(String language) {
        navigator.on(defaultTab, page -> {
            page.selectSettingForDefault("Select default language", language);
            page.checkDefaultSettingsForDefault("Select default language");
        });
    }


    @Given("{string} set {string} setting {string}")
    public void setSettingTrue(String user, String setting, String flag) {
        navigator.loginAsUser(userTab,user, page -> {
            page.navigateAndVerifyPage();
            page.selectNonDefaultSettings(setting,flag);
        });
    }
}