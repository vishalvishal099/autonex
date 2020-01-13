package com.oracle.babylon.pages.Setup;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class PreferencesPage extends Navigator {
    protected By prefTable = By.xpath("//table[@class='indented dataTable preferences-table']");
    protected By saveButton = By.xpath("//div[contains(text(),'Save')]");


    public void selectDefaultSettings(String preference) {
        int prefRow = getPrefRow(preference);
        int defaultSettingfCol = 3;
        WebElement checkBox = driver.findElement(prefTable).findElement(By.xpath("//tr[" + prefRow + "]//td[" + defaultSettingfCol + "]//input[@type='checkbox']"));
        if (!checkBox.isSelected()) {
            checkBox.click();
            $(saveButton).click();
        } else if (checkBox.isSelected()) {
            $(saveButton).click();
        }
    }

    public void clickEditButtonForSetting(String preference) {
        int prefRow = getPrefRow(preference);
        int prefCol = 2;
        WebElement editButton = driver.findElement(prefTable).findElement(By.xpath("//tr[" + prefRow + "]//td[" + prefCol + "]//div[@class='uiButton-label'][contains(text(),'Edit')]"));
        editButton.click();
        System.out.println("pass");
    }

    public void selectSettingFrom(String preference, String option) {
        int prefRow = getPrefRow(preference);
        int settingCol = 2;
        int defaultSettingColumn = 3;
        WebElement checkBox = driver.findElement(prefTable).findElement(By.xpath("//tr[" + prefRow + "]//td[" + defaultSettingColumn + "]//input[@type='checkbox']"));
        if (!checkBox.isSelected()) {
            selectOption(option, prefRow, settingCol);
        } else if (checkBox.isSelected()) {
            checkBox.click();
            selectOption(option, prefRow, settingCol);
        }
    }

    public void selectOption(String option, int row, int column) {
        WebElement selectOptionFromList = driver.findElement(prefTable).findElement(By.xpath("//tr[" + row + "]//td[" + column + "]//select"));
        $(selectOptionFromList).selectOption(option);
        $(saveButton).click();
    }

    public void selectNonDefaultSettings(String preference) {
        int prefRow = getPrefRow(preference);
        int defaultsettingCol = 3;
        int settingColumn = 2;
        WebElement defaultSettingcheckBox = driver.findElement(prefTable).findElement(By.xpath("//tr[" + prefRow + "]//td[" + defaultsettingCol + "]//input[@type='checkbox']"));
        WebElement settingCheckbox = driver.findElement(prefTable).findElement(By.xpath("//tr[" + prefRow + "]//td[" + settingColumn + "]//input[@type='checkbox']"));
        if (defaultSettingcheckBox.isSelected()) {
            defaultSettingcheckBox.click();
            if (settingCheckbox.isSelected()) {
                $(saveButton).click();
            } else {
                settingCheckbox.click();
                $(saveButton).click();
            }
        }
    }

    public int getPrefRow(String prefName) {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        List<WebElement> prefLists;
        prefLists = driver.findElement(prefTable).findElements(By.xpath("//tr//td[1]"));
        int prefRow = 0;
        for (int i = 1; i < prefLists.size(); i++) {
            if (prefLists.get(i).getText().equals(prefName)) {
                prefRow = i + 3;
                break;
            }
        }
        return prefRow;
    }
}