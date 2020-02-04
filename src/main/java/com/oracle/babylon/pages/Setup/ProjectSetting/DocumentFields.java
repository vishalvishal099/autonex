package com.oracle.babylon.pages.Setup.ProjectSetting;

import com.oracle.babylon.pages.Setup.ProjectSettingsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class DocumentFields extends ProjectSettingsPage {
    private By docTable = By.xpath("//table[@class='dataTable']");
    private By saveButton = By.xpath("//button[@id='btnSave_page']//div[@class='uiButton-label'][contains(text(),'Save')]");
    private By lockFieldsLabel = By.xpath("//div[contains(text(),'Lock field labels')]");
    private By saveChanges = By.xpath("//div[contains(text(),'Save Changes')]");

    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Project Settings");
        commonMethods.waitForElementExplicitly(5000);
        switchTo().frame("frameMain");
        $(By.xpath("//span[contains(text(),'Documents')]")).click();
        commonMethods.waitForElementExplicitly(2000);
        $(By.xpath("//div[contains(text(),'Document Fields')]")).click();
//        verifyPageTitle("Project Settings");
    }

    public void lockFieldsLabel() {
        $(lockFieldsLabel).click();
    }

    public void copyFromProject() { $(By.xpath("//div[contains(text(),'Copy From Project')]")).click(); }

    public void preview() { $(By.xpath("//div[contains(text(),'Preview')]")).click(); }


    public void useField(String label, String checkbox) {
        boolean flag = Boolean.parseBoolean(checkbox.toLowerCase());
        int row = getLabelRow(label);
        WebElement chkBox = $(By.xpath("//table[@class='dataTable']//tr[" + row + "]//td[2]//input[@type='checkbox']"));
        if (flag) {
            if (!chkBox.isSelected()) {
                chkBox.click();
            }
        } else if (!flag) {
            if (chkBox.isSelected()) {
                chkBox.click();
            }
        }
        $(saveButton).click();
    }

    public void setMandatory(String label, String checkbox) {
        boolean flag = Boolean.parseBoolean(checkbox.toLowerCase());
        int row = getLabelRow(label);
        WebElement chkBox = $(By.xpath("//table[@class='dataTable']//tr[" + row + "]//td[3]//input[@type='checkbox']"));
        if (flag) {
            if (!chkBox.isSelected()) {
                chkBox.click();
            }
        } else if (!flag) {
            if (chkBox.isSelected()) {
                chkBox.click();
            }
        }
        $(saveButton).click();
    }

    public void setEditableInline(String label, String checkBox) {
        boolean flag = Boolean.parseBoolean(checkBox.toLowerCase());
        int row = getLabelRow(label);
        WebElement chkBox = $(By.xpath("//table[@class='dataTable']//tr[" + row + "]//td[7]//input[@type='checkbox']"));
        if (flag) {
            if (!chkBox.isSelected()) {
                chkBox.click();
            }
        } else if (!flag) {
            if (chkBox.isSelected()) {
                chkBox.click();
            }
        }
        $(saveButton).click();
    }


    public void editListValues(String label) {
        int row = getLabelRow(label);
        WebElement editLink = $(By.xpath("//table[@class='dataTable']//tr[" + row + "]//td[6]//a"));
        editLink.click();
        $(saveChanges).click();
    }


    public int getLabelRow(String label) {
        switchTo().frame("project-settings-page");
        commonMethods.waitForElementExplicitly(5000);
        List<WebElement> labelLists;
        labelLists = driver.findElement(docTable).findElements(By.xpath("//tr"));
        int labelRow = 0;
        for (int i = 3; i < labelLists.size(); i++) {
            if (labelLists.get(i).getText().contains(label)) {
                labelRow = i;
                break;
            }
        }
        return labelRow - 2;
    }
}

