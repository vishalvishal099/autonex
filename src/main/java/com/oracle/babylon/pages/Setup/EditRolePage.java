package com.oracle.babylon.pages.Setup;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

public class EditRolePage extends Navigator {
    protected WebDriver driver = null;
    private By roleNameField = By.name("RoleName");
    private By assignRoleToNewMember = By.xpath("//input[@name='DefaultRole']");
    private By save = By.xpath("//div[@class='uiButton-label']");


    public void editRoleNameAndFlag(String roleName, boolean assignRoleToNewMemberFlag) {
        $(roleNameField).clear();
        $(roleNameField).sendKeys(roleName);
        if (assignRoleToNewMemberFlag) {
            if (!$(assignRoleToNewMember).isSelected()){
                $(assignRoleToNewMember).click();
            }

        }
        else if(!assignRoleToNewMemberFlag){
            if ($(assignRoleToNewMember).isSelected()){
                $(assignRoleToNewMember).click();
            }
        }
        $(save).click();
    }
}
