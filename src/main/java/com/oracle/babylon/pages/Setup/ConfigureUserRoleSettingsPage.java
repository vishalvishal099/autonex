package com.oracle.babylon.pages.Setup;

import com.codeborne.selenide.Condition;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to core configure user/role page
 * Author : kukumavi
 */

public class ConfigureUserRoleSettingsPage extends Navigator {
    protected Navigator navigator = new Navigator();
    protected CommonMethods commonMethods = new CommonMethods();
    protected By createRoleButton = By.xpath("//div[contains(text(),'Create Role')]");
    protected By assignUserRoleButton = By.xpath("//div[contains(text(),'Assign User Roles')]");
    protected By saveButton = By.xpath("//div[contains(text(),'Save')]");
    protected By assetTable = By.xpath("//table[@id='roleSecuredAssetTable']");


    public void navigateAndVerifyPage() {
        navigator.getMenuSubmenu("Setup", "Configure User Role Settings");
        navigator.verifyPageTitle("Configure User Role Settings");
    }

    public void verifyCreateRole() {
        $(createRoleButton).shouldBe(Condition.visible);
    }

    public void verifyUserRoleButton() {
        $(assignUserRoleButton).shouldBe(Condition.visible);
    }

    public void saveButton() {
        $(saveButton).shouldBe(Condition.visible);
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
        //Ask sushant about the accept alert function

    }

    public int getAssetRow(String parentAsset, String assetName) {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        List<WebElement> roleLists = driver.findElement(assetTable).findElements(By.xpath("//td"));
        List<WebElement> assetLists;
        assetLists = driver.findElement(assetTable).findElements(By.xpath("//tr"));
        int assetRow = 0;
        int assetGroup = 0;
        for (int i = 1; i < assetLists.size(); i++) {
            if (assetLists.get(i).getText().contains(parentAsset)) {
                assetGroup = i + 1;
                System.out.println(assetGroup);
                break;
            }
        }
        for (int j = assetGroup; j < assetLists.size(); j++) {
            if (assetLists.get(j).getText().contains(assetName)) {
                assetRow = j + 1;
                System.out.println(assetRow);
                break;
            }
        }
        return assetRow;
    }

    public int getRoleColumn(String roleName) {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        List<WebElement> roleLists = driver.findElement(assetTable).findElements(By.xpath("//td"));
        int roleColumn = 0;
        for (int i = 1; i < roleLists.size(); i++) {
            if (roleLists.get(i).getText().contains(roleName)) {
                roleColumn = i + 1;
                System.out.println(roleColumn);
                break;
            }
        }
        return roleColumn;
    }

}
