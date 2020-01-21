package com.oracle.babylon.pages.Setup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class PreferenceUserTab extends PreferencesPage {


    public void navigateAndVerifyPage() {
        commonMethods.waitForElementExplicitly(2000);
        getMenuSubmenu("Setup", "Preferences");
        verifyPageTitle("Edit Preferences");
    }

    public void checkDefaultSettingsUser(String preference) {
        selectDefaultSettings(preference);
    }


//    public void userPreference(String preference) {
//        int prefRow = getPrefRow(preference);
//        System.out.println(prefRow);
//    }

    public void selectSettingForUser(String preference, String option) {
        selectSettingFrom(preference, option);
    }

    public void editSettingForUser(String preferences) {
        clickEditButtonForSetting(preferences);
    }

    public void checkNonDefaultSettingsForUser(String preference, String flag) {
        selectNonDefaultSettings(preference, flag);
    }

    public void clickSave(){
        $(By.xpath("//div[@class='uiButton-label'][contains(text(),'Save')]"));
        $(By.xpath("//div[contains(text(),'Close')]"));
    }

}
