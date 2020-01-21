package com.oracle.babylon.pages.Setup;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class PreferenceDefaultTab extends PreferencesPage {
    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Preferences");
        switchTo().frame("main");
        $(By.xpath("//li[@id='default-tab']")).click();
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

    public void checkNonDefaultSettingsForDefault(String preference, String flag) {
        selectNonDefaultSettings(preference, flag);
    }
    public void clickSave(){
        $(By.xpath("//div[contains(text(),'Save')]"));
        $(By.xpath("//div[contains(text(),'Close')]"));
    }
}
