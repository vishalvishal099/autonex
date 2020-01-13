package com.oracle.babylon.pages.Setup;

import static com.codeborne.selenide.Selenide.$;

public class PreferenceOrganizationTab extends PreferencesPage {
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Preferences");
        $("#organization-tab").click();
        verifyPageTitle("Edit Preferences");
    }

    public void checkDefaultSettingsForOrganization(String preference) {
        selectDefaultSettings(preference);
    }

    public void selectSettingForOrganization(String preference, String option) {
        selectSettingFrom(preference, option);
    }

    public void editSettingForOrg(String preferences) {
        clickEditButtonForSetting(preferences);
    }

    public void checkNonDefaultSettingsForOrganization(String preference) {
        selectNonDefaultSettings(preference);
    }

}
