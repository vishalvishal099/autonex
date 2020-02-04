package com.oracle.babylon.pages.Setup;

import static com.codeborne.selenide.Selenide.$;

public class PreferenceDefaultTab extends PreferencesPage {
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Preferences");
        $("#default-tab").click();
        verifyPageTitle("Edit Preferences");
    }

    public void checkDefaultSettingsForDefault(String preference) {
        selectDefaultSettings(preference);
    }

    public void selectSettingForDefault(String preference, String option) {
        selectSettingFrom(preference, option);
    }

    public void editSettingForDefault(String preferences) {
        clickEditButtonForSetting(preferences);
    }

    public void checkNonDefaultSettingsForDefault(String preference) {
        selectNonDefaultSettings(preference);
    }
}
