package com.oracle.babylon.pages.Setup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class ConfigureUserRoleProjectTab extends ConfigureUserRoleSettingsPage {
    private By assetTableProject = By.xpath("//table[@class='formTable']");


    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Configure User Role Settings");
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $("#ORG_AND_PROJECT_list").click();
        verifyPageTitle("Configure User Role Settings");
    }

    public void selectProject(String project) {
        $("#SECURITY_ADMIN_PROJECT_ID").selectOption(project);
            }

    public void deleteProjectRole(String project, String role) {
        selectProject(project);
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        List<WebElement> roleLists = driver.findElement(assetTable).findElements(By.xpath("//td"));
        List<WebElement> assetLists;
        assetLists = driver.findElement(assetTable).findElements(By.xpath("//tr"));
        int assetSize = assetLists.size();
        int roleIndex = getProjectRoleColumn(role);
        WebElement trashIcon = driver.findElement(assetTable).findElement(By.xpath("//tr[" + assetSize + "]//td[" + roleIndex + "]//a[1]//img[1]"));
        $(trashIcon).click();
        driver.switchTo().alert().accept();
    }




}
