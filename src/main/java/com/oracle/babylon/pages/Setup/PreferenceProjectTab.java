package com.oracle.babylon.pages.Setup;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class PreferenceProjectTab extends PreferencesPage {
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Preferences");
        switchTo().frame("frameMain");
        $("#project-tab").click();
        verifyPageTitle("Edit Preferences");
    }

    public void checkDefaultSettingsForProject(String preference) {
        selectDefaultSettings(preference);
    }

    public void selectSettingForProject(String preference, String option) {
        selectSettingFrom(preference, option);
    }

    public void editSettingForProject(String preferences) {
        clickEditButtonForSetting(preferences);
    }

    public void checkNonDefaultSettingsForProject(String preference, String flag) {
        selectNonDefaultSettings(preference, flag);
    }
}
