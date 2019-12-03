package com.oracle.babylon.pages.Setup;

import com.codeborne.selenide.Condition;
import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.oracle.babylon.Utils.helper.CommonMethods;
import java.util.List;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to core user/role page
 * Author : kukumavi
 */

public class AssignUserRolePage extends Navigator {

    protected By givenName = By.xpath("//input[@id='display.givenName']");
    protected By familyName = By.xpath("//input[@id='display.familyName']");
    protected By search = By.xpath("//div[contains(text(),'Search')]");
    protected By projectTab = By.xpath("//li[@id='ORG_AND_PROJECT_list']");
    protected By disabledAccount = By.cssSelector("#display.showDisabled");
    protected By next = By.linkText("next");
    protected By previous = By.linkText("previous");
    protected By createRoleButton = By.xpath("//div[contains(text(),'Create Role')]");
    protected By configureUserRoleSettingsButton = By.xpath("//div[contains(text(),'Configure User Role Settings')]");
    protected By saveButton = By.xpath("//div[contains(text(),'Save')]");
    protected By resultTable = By.xpath("//table[@id='resultTable']");
    protected By disabledUser = By.xpath("//input[@id='display.showDisabled']");
    protected By organizationTab = By.xpath("//li[@id='ORG_WIDE_list']");


    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Assign User Roles");
        verifyPageTitle("Roles / User");
    }

    public void selectProjectTab(){
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $(projectTab).click();
    }

    public void searchUser(String user) {
        String[] currencies = user.split(" ");
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $(givenName).sendKeys(currencies[0]);
        $(familyName).sendKeys(currencies[currencies.length - 1]);
        $(search).click();
    }

    public void showDisabledAccount() {
        if (!$(disabledAccount).isSelected())
            $(disabledAccount).click();
    }
    public void verifyCreateRole() {
        $(createRoleButton).shouldBe(Condition.visible);
    }

    public void verifyConfigureUserRoleSettings() {
        $(configureUserRoleSettingsButton).shouldBe(Condition.visible);
    }

    public void saveButton() {
        $(saveButton).shouldBe(Condition.visible);
    }


}
