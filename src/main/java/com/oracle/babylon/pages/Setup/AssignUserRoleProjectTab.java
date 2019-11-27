package com.oracle.babylon.pages.Setup;
import org.junit.Assert;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to user/role page project tab
 * Author : kukumavi
 */

public class AssignUserRoleProjectTab extends AssignUserRolePage {
    public void navigateAndVerifyPage(){
        navigator.getMenuSubmenu("Setup", "Assign User Roles");
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $(projectTab).click();
        navigator.verifyPageTitle("Roles / User");
    }
    public void selectProject(String project) {
        $("#SECURITY_ADMIN_PROJECT_ID").selectOption(project);
    }
    public void verifyUserInProject(String user, String project) {
        selectProject(project);
        if ($(disabledUser).isSelected()) {
            $(disabledUser).click();
        }
        searchUser(user);
        By searchedUser = By.cssSelector(".dataRow");
        commonMethods.waitForElementExplicitly(2000);
        Assert.assertTrue($(searchedUser).text().contains(user));
    }
    public void verifyDisabledUserInProject(String user, String project) {
        selectProject(project);
        if (!$(disabledUser).isSelected()) {
            $(disabledUser).click();
        }
        searchUser(user);
        By searchedUser = By.cssSelector(".dataRow");
        commonMethods.waitForElementExplicitly(2000);
        Assert.assertTrue($(searchedUser).text().contains(user));
    }
    public void selectOrganizationTab() {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $(organizationTab).click();
    }
}
