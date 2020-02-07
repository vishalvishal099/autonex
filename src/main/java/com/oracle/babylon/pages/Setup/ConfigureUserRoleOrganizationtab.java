package com.oracle.babylon.pages.Setup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to configure user/role org tab
 * Author : kukumavi
 */

public class ConfigureUserRoleOrganizationtab extends ConfigureUserRoleSettingsPage {


    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Configure User Role Settings");
        verifyPageTitle("Configure User Role Settings");
    }

    public void setAsset(String parentAsset, String assetName, String roleName, String flag) {
        String rowIndex = Integer.toString(getAssetRow(parentAsset, assetName));
        String columnIndex = Integer.toString(getRoleColumn(roleName));
        WebElement grantOption = driver.findElement(assetTable).findElement(By.xpath("//tr[" + rowIndex + "]//td[" + columnIndex + "]/select"));
        $(grantOption).selectOptionByValue(flag);
        $(saveButton).click();
    }

    public void deleteRole(String role) {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        List<WebElement> roleLists = driver.findElement(assetTable).findElements(By.xpath("//td"));
        List<WebElement> assetLists;
        assetLists = driver.findElement(assetTable).findElements(By.xpath("//tr"));
        int assetSize = assetLists.size();
        int roleIndex = getRoleColumn(role);
        WebElement trashIcon = driver.findElement(assetTable).findElement(By.xpath("//tr[" + assetSize + "]//td[" + roleIndex + "]//a[1]//img[1]"));
        $(trashIcon).click();
        driver.switchTo().alert().accept();
    }


}
