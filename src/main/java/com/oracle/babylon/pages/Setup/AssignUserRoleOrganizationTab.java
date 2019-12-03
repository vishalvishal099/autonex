package com.oracle.babylon.pages.Setup;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Function that contains the methods related to user/role page org tab
 * Author : kukumavi
 */

public class AssignUserRoleOrganizationTab extends AssignUserRolePage {
    private EditRolePage editRolePage = new EditRolePage();

    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Assign User Roles");
        verifyPageTitle("Roles / User");
    }

    public void checkboxUserRole(String user, String role, String flag) {
        searchUser(user);
        System.out.println(user);
        By searchedUser = By.cssSelector(".dataRow");
        commonMethods.waitForElementExplicitly(2000);
        Assert.assertTrue($(searchedUser).text().contains(user));
        String columnIndex = Integer.toString(roleColumnIndex(role));
        WebElement checkBox = driver.findElement(resultTable).findElement(By.xpath("//tr[1]//td[" + columnIndex + "]//input[@type='checkbox']"));
        switch (flag) {
            case "assigned":
                if (!checkBox.isSelected()) {
                    checkBox.click();
                }
                break;
            case "unassigned":
                if (checkBox.isSelected()) {
                    checkBox.click();
                }
                break;
        }
        $(saveButton).click();
    }

    public void selectProjectTab() {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $(projectTab).click();
    }


    public void nextPage() {
        $(next).click();
    }

    public void previousPage() {
        $(previous).click();
    }

    public void gotoPage(String page) {
        By pageElement = By.linkText(page);
        $(pageElement).click();
    }

    public void editRole(String roleName,String newRoleName, boolean assignRoleToNewMemberFlag) {
        String columnIndex = Integer.toString(roleColumnIndex(roleName));
        By editRole = By.xpath("//table[@id='resultTable']//tr[@class='dataHeaders']//th[" + columnIndex + "]/a[contains(@title,'Edit security role')]");
        $(editRole).click();
        editRolePage.editRoleNameAndFlag(newRoleName, assignRoleToNewMemberFlag);
    }

    public int roleColumnIndex(String roleName) {
        List<WebElement> listOfColumns = driver.findElement(resultTable).findElements(By.xpath("//thead//tr//th"));
        int column_index = 0;
        for (WebElement e : listOfColumns) {
            String actualRole = e.getText();
            column_index = column_index + 1;
            if (roleName.equals(actualRole)) {
                break;
            }
        }
        return column_index;
    }
}
