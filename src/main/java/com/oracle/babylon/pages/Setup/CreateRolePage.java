package com.oracle.babylon.pages.Setup;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class CreateRolePage extends Navigator {

    private By createRole = By.xpath("//div[contains(text(),'Create Role')]");
    private By roleNameField = By.name("RoleName");
    private By assignRoleToNewMember = By.xpath("//input[@name='DefaultRole']");
    private By save = By.xpath("//div[@class='uiButton-label']");


    public void navigateAndVerifyPage(){
        getMenuSubmenu("Setup", "Assign User Roles");
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        $(createRole).click();
        verifyPageTitle("New Role");
    }

    public void createRole(String roleName, boolean assignRoleToNewMemberFlag){
        $(roleNameField).clear();
        $(roleNameField).sendKeys(roleName);
        if (assignRoleToNewMemberFlag){
            $(assignRoleToNewMember).click();
        }
        $(save).click();
    }


}
