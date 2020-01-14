package com.oracle.babylon.pages.Setup.ProjectSetting;

import com.oracle.babylon.Utils.helper.Navigator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class MailTypeSetting extends ProjectSettingsPage {
    private By mailTypeTab = By.xpath("//div[contains(text(),'Mail Types')]");
    private By resultTable = By.xpath("//table[@class='auiTable']");
    private By newField = By.xpath("//button[contains(text(),'+ New Field')]");
    private By selectType = By.xpath("//div[@class='form-section']//select[@name='type']");
    private By label = By.xpath("//input[@name='label']");
    private By newFieldSave = By.xpath("//button[@name='saveButton']");

    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Project Settings");
        switchTo().frame("frameMain");
        $(mailTypeTab).click();
//        verifyPageTitle("Project Settings");
    }

    public void editMailForms(String mailType) {
        String mailTypeRow = Integer.toString(getMailTypeRow(mailType));
        WebElement checkBox = driver.findElement(resultTable).findElement(By.xpath("//tr[" + mailTypeRow + "]//td[2]//a"));
        checkBox.click();
    }

    public void editRestrictedFields(String mailType) {
        String mailTypeRow = Integer.toString(getMailTypeRow(mailType));
        WebElement checkBox = driver.findElement(resultTable).findElement(By.xpath("//tr[" + mailTypeRow + "]//td[3]//a"));
        checkBox.click();
    }

    public void editRules(String mailType) {
        String mailTypeRow = Integer.toString(getMailTypeRow(mailType));
        WebElement checkBox = driver.findElement(resultTable).findElement(By.xpath("//tr[" + mailTypeRow + "]//td[4]//a"));
        checkBox.click();
    }


    public int getMailTypeRow(String mailType) {
        switchTo().frame("project-settings-page");
        commonMethods.waitForElementExplicitly(5000);
        List<WebElement> typeLists;
        typeLists = driver.findElement(resultTable).findElements(By.xpath("//tr"));
        int mailTypeRow = 0;
        for (int i = 1; i < typeLists.size(); i++) {
            if (typeLists.get(i).getText().contains(mailType)) {
                mailTypeRow = i + 1;
                break;
            }
        }
        return mailTypeRow + 2;
    }

    public void clickNewField() {
        $(newField).click();
    }

    public void addNewField(String mailType, String fieldParams) {
        editMailForms(mailType);
        Map<String, String> table = dataStore.getTable(fieldParams);
        for (String tableData : table.keySet()) {
            switch (tableData) {
                case "Yes/No":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] values = table.get(tableData).split(",");
                    $(label).sendKeys(values[0]);
                    $(newFieldSave).click();
                    break;
                case "Date":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] dateValues = table.get(tableData).split(",");
                    $(label).sendKeys(dateValues[0]);
                    $(newFieldSave).click();
                    break;
                case "Text":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] textValues = table.get(tableData).split(",");
                    $(label).sendKeys(textValues[0]);
                    $(newFieldSave).click();
                    break;
                case "Text Area":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] textAreaValues = table.get(tableData).split(",");
                    $(label).sendKeys(textAreaValues[0]);
                    $(newFieldSave).click();
                    break;
                case "Number":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] numberValues = table.get(tableData).split(",");
                    $(label).sendKeys(numberValues[0]);
                    $(newFieldSave).click();
                    break;
                case "Select List (Single)":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] singleListValues = table.get(tableData).split(",");
                    $(label).sendKeys(singleListValues[0]);
                    $(By.xpath("//textarea[@placeholder='Enter each option on a new line']")).sendKeys(singleListValues[1]);
                    $(newFieldSave).click();
                    break;
                case "Select List (Multiple)":
                    clickNewField();
                    $(selectType).selectOptionContainingText(tableData);
                    String[] multipleListValues = table.get(tableData).split(",");
                    $(label).sendKeys(multipleListValues[0]);
                    $(By.xpath("//input[@placeholder='Enter names to add users']")).sendKeys(multipleListValues[1]);
                    $(newFieldSave).click();
                    break;
            }
        }
    }
}




