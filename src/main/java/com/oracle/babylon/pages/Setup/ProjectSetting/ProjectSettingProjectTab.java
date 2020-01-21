package com.oracle.babylon.pages.Setup.ProjectSetting;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

import java.util.Map;

public class ProjectSettingProjectTab extends ProjectSettingsPage {

    protected By projectName = By.xpath("//input[@name='ProjectName']");
    protected By projectShortName = By.xpath("//input[@name='ProjectShortName']");
    protected By projectCode = By.xpath("//input[@name='ProjectCode']");
    protected By projectType = By.xpath("//input[@name='ProjectType']");
    protected By registerType = By.xpath("//select[@id='RegisterType']");
    protected By accessLevel = By.xpath("//select[@id='selectDefaultAccessLevel']");
    protected By phoneNumber = By.xpath("//input[@name='PhoneNbr']");
    protected By phoneCountry = By.xpath("//input[@name='PhoneCountry']");
    protected By phoneArea = By.xpath("//input[@name='PhoneArea']");
    protected By faxNumber = By.xpath("//input[@name='FaxNbr']");
    protected By projectAddress = By.xpath("//input[@name='ProjectAddress1']");
    protected By projectCity = By.xpath("//input[@name='ProjectSuburb']");
    protected By projectCountry = By.xpath("//select[@name='ProjectCountry']");
    protected By projectStartDate = By.xpath("//input[@id='ProjectStartDate_da']");
    protected By projectEndDate = By.xpath("//input[@id='ProjectStopDate_da']");
    protected By save = By.xpath("//div[contains(text(),'Save')]");
    protected By CopyProject = By.xpath("//div[contains(text(),'Copy')]");
    protected By postCode = By.xpath("//input[@name='ProjectPostcode']");
    protected By copyProjectInfoFlag =  By.xpath("//input[@name='CanAdminUsersCopyProject' and @type='checkbox']");


    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Project Settings");
        verifyPageTitle("Project Settings");
    }


    public void editProjectDetails(String data) {
        commonMethods.switchToFrame(driver, "frameMain");
        commonMethods.switchToFrame(driver, "project-settings-page");
        Map<String, String> table = dataStore.getTable(data);
        //According to the keys passed in the table, we select the fields
        for (String tableData : table.keySet()) {
            switch (tableData) {
                case "Project Name":
                    $(projectName).clear();
                    $(projectName).sendKeys(table.get(tableData));
                    break;
                case "Project Short Name":
                    $(projectShortName).clear();
                    $(projectShortName).sendKeys(table.get(tableData));
                    break;
                case "Project Code":
                    $(projectCode).clear();
                    $(projectCode).sendKeys(table.get(tableData));
                    break;
                case "Project Type":
                    $(projectType).clear();
                    $(projectType).sendKeys(table.get(tableData));
                    break;
                case "Primary Register Type":
                    $(registerType).selectOptionByValue(table.get(tableData));
                    break;
                case "Default Access Level":
                    $(accessLevel).selectOptionByValue(table.get(tableData));
                    break;
                case "Phone Number":
                    String phone = table.get(tableData);
                    String[] splitPhoneNumber = phone.split("\\s+");
                    $(phoneCountry).clear();
                    $(phoneArea).clear();
                    $(phoneNumber).clear();
                    $(phoneCountry).sendKeys(splitPhoneNumber[0]);
                    $(phoneArea).sendKeys(splitPhoneNumber[1]);
                    $(phoneNumber).sendKeys(splitPhoneNumber[2]);
                    break;
                case "Project Address 1":
                    $(projectAddress).clear();
                    $(projectAddress).sendKeys(table.get(tableData));
                    break;
                case "City/Suburb":
                    $(projectCity).clear();
                    $(projectCity).sendKeys(table.get(tableData));
                    break;
                case "Country":
                    $(projectCountry).clear();
                    $(projectCountry).sendKeys(table.get(tableData));
                    break;
                case "Postcode":
                    $(postCode).clear();
                    $(postCode).sendKeys(table.get(tableData));
                    break;
                case "Project Start Date":
//                    $(projectStartDate).sendKeys("");
//                    Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
//                    date function to be written
                    break;
                case "Estimated Completion Date":
//                    $(projectEndDate).sendKeys("");
//                    Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
//                    date function to be written
                    break;
            }
        }
        $(save).click();
    }
}
