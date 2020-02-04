package com.oracle.babylon.pages.Setup.ProjectSetting;

import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class MailDocumentsRoleSetting extends ProjectSettingsPage {
    protected By mailDocRoleSettingMenu = By.xpath("//div[contains(text(),'Mail/Documents Role Settings')]");
    private By createRole = By.xpath("//div[contains(text(),'Create Role')]");
    private By showAllOption = By.cssSelector("#SHOW_ALL");
    private By cancelRole = By.xpath("//button[@id='btnrolePanel_cancel']//div[@class='uiButton-label'][contains(text(),'Cancel')]");
    private By defaultRole = By.xpath("//input[@id='ROLE_IS_DEFAULT']");
    private By roleName = By.xpath("//input[@id='ROLE_NAME']");
    private By corrTypeAddBtn = By.xpath("//button[@id='btnSELECTED_CORR_TYPES_ADD']//div[@class='uiButton-label'][contains(text(),'>>')]");
    private By docTypeAddBtn = By.xpath("//button[@id='btnSELECTED_DOC_TYPES_ADD']//div[@class='uiButton-label'][contains(text(),'>>')]");
    private By docStatusAddBtn = By.xpath("//button[@id='btnSELECTED_DOC_STATUSES_ADD']//div[@class='uiButton-label'][contains(text(),'>>')]");
    private By okBtn = By.xpath("//button[@id='btnrolePanel_ok']//div[@class='uiButton-label'][contains(text(),'OK')]");
    private By copyFromProject = By.xpath("//div[@class='uiButton-label'][contains(text(),'Copy From Project')]");
    private By selectProject = By.xpath("//select[@id='FROM_PROJECT_ID']");
    private By confirmCopyFromProject = By.xpath("//button[@id='btncopyFromProjectPanel_ok']//div[@class='uiButton-label'][contains(text(),'OK')]");
    private By resultTable = By.xpath("//table[@class='blank']");
    private By saveBtn = By.xpath("//td[@class='toolbar clearFloats']//button[@id='btnSave']");
    private By deleteRoleBtn = By.xpath("//div[contains(text(),'Delete Role')]");


    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Project Settings");
        switchTo().frame("frameMain");
        $(mailDocRoleSettingMenu).click();
//        verifyPageTitle("Project Settings");
    }

    public void createNewRole() {
        switchTo().frame("project-settings-page");
        $(createRole).click();
    }

    public void showAllAvailableOption() {
        switchTo().frame("project-settings-page");
        $(showAllOption).click();
    }

    public void closeCreateRolePanel() {
        switchTo().frame("project-settings-page");
        $(cancelRole).click();
    }

    public void selectTab(String tab) {
        switchTo().frame("project-settings-page");
        switch (tab) {
            case "Mail Types":
                $(By.cssSelector("#CORR_TYPES")).click();
                break;
            case "Doc Types":
                $(By.cssSelector("#DOC_TYPES")).click();
                break;
            case "Doc Statuses":
                $(By.cssSelector("#DOC_STATUSES")).click();
                break;
            case "Review Status Sets":
                $(By.cssSelector("#RSS_TYPES")).click();
                break;
        }

    }

    public void addTypes(String tab, String type) {
//        switchTo().frame("project-settings-page");
        String commonPath = "//div[@class='uiBidi-left']//select//option[text() = '";
        switch (tab) {
            case "Mail Types":
                $(By.xpath("//div[@id='CORR_TYPESDiv']" + commonPath + type + "']")).click();
                $(corrTypeAddBtn).click();
                break;
            case "Doc Types":
                $(By.xpath("//div[@id='DOC_TYPESDiv']" + commonPath + type + "']")).click();
                $(docTypeAddBtn).click();
                break;
            case "Doc Statuses":
                $(By.xpath("//div[@id='DOC_STATUSESDiv']" + commonPath + type + "']")).click();
                $(docStatusAddBtn).click();
                break;
        }
    }


    public void createRole(String type, String name, String roleParams) {
        switchTo().frame("project-settings-page");
        $(createRole).click();
        switch (type.toLowerCase()) {
            case "default":
                $(defaultRole).click();
                break;
            case "normal":
                break;
        }
        $(roleName).sendKeys(name);
        System.out.println("h");
        Map<String, String> table = dataStore.getTable(roleParams);
        for (String tableData : table.keySet()) {
            switch (tableData) {
                case "Mail Types":
                    $(By.xpath("//li[@id='CORR_TYPES']")).click();
                    String[] mailTypeValues = table.get(tableData).split(",");
                    for (int i = 0; i < mailTypeValues.length; i++) {
                        addTypes(tableData, mailTypeValues[i]);
                    }
                    break;
                case "Doc Types":
                    $(By.xpath("//li[@id='DOC_TYPES']")).click();
                    String[] doctypeValues = table.get(tableData).split(",");
                    for (int i = 0; i < doctypeValues.length; i++) {
                        addTypes(tableData, doctypeValues[i]);
                    }
                    break;
                case "Doc Statuses":
                    $(By.xpath("//li[@id='DOC_STATUSES']")).click();
                    String[] docStatusValues = table.get(tableData).split(",");
                    for (int i = 0; i < docStatusValues.length; i++) {
                        addTypes(tableData, docStatusValues[i]);
                    }
                    break;
            }
        }
        $(okBtn).click();
    }

    public void verifyNewRoleName(String name) {
        commonMethods.waitForElementExplicitly(5000);
        Assert.assertTrue($(By.xpath("//table[@class='blank']//thead")).text().contains(name));
    }

    public void copyFromProject(String project) {
        switchTo().frame("project-settings-page");
        $(copyFromProject).click();
        $(selectProject).selectOptionContainingText(project);
        $(confirmCopyFromProject).click();
    }

    public void deleteRole(String roleName) {
        String columnIndex = Integer.toString(roleColumnIndex(roleName));
        By role = By.xpath("//table[@class='blank']//thead//tr//th[" + columnIndex + "]/a");
        $(role).click();
        //Please define the identifiers on top
        WebElement defaultRole = $(By.xpath("//div[@id='rolePanel_body']//table[@class='formTable']//tr[2]/td[2]/input[@class ='checkbox']"));
        if (defaultRole.isSelected()) {
            defaultRole.click();
            $(okBtn).click();
            commonMethods.waitForElementExplicitly(2000);
            $(role).click();
        }
        $(deleteRoleBtn).click();
    }

    public void deselectAllOrganisation(String roleName) {
        String columnIndex = Integer.toString(roleColumnIndex(roleName));
        //table[@class='blank']//tr[4]//td[3]/input
        List<WebElement> listOfRows = driver.findElement(resultTable).findElements(By.xpath("//tr//td[1]"));
        int rowSize;
        rowSize = listOfRows.size() - 8;
        for (int i = 1; i <= rowSize; i++) {
            WebElement orgAssociation = $(By.xpath("//table[@class='blank']//tr[" + i + "]//td[" + columnIndex + "]/input"));
            if (orgAssociation.isSelected()) {
                orgAssociation.click();
            }
        }
        $(saveBtn).click();
    }

    public int roleColumnIndex(String roleName) {
        switchTo().frame("project-settings-page");
        commonMethods.waitForElementExplicitly(2000);
        List<WebElement> listOfColumns = driver.findElement(resultTable).findElements(By.xpath("//thead//tr//th"));
        int column_index = 0;
        for (WebElement e : listOfColumns) {
            String actualRole = e.getText();
            column_index = column_index + 1;
            if (actualRole.contains(roleName)) { //No need for extra brackets
                break;
            }
        }
        return column_index;
    }

}