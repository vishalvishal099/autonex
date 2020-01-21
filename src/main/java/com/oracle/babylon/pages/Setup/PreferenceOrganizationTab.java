package com.oracle.babylon.pages.Setup;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class PreferenceOrganizationTab extends PreferencesPage {
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Preferences");
        switchTo().frame("main");
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

    public void checkNonDefaultSettingsForOrganization(String preference, String flag) {
        selectNonDefaultSettings(preference, flag);
    }

    public void clickSave(){
        $(By.xpath("//div[contains(text(),'Save')]"));
        $(By.xpath("//div[contains(text(),'Close')]"));
    }

}
