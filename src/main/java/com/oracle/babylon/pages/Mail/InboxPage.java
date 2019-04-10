package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.worldHelper.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

public class InboxPage {
    WebDriver driver = WebDriverRunner.getWebDriver();
    Navigator navigator = new Navigator();

    public void selectMenuSubMenu() {
        navigator.getMenuSubmenu("Mail", "Inbox");
        navigator.switchToFrame("frameMain");
    }

    public void searchMailNumber(String mail_number) {
        $(By.id("rawQueryText")).sendKeys(mail_number);
        $(".loading_progress").should(disappear);
    }

    private void waitForSearchButtonForBeActive() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitForSearchLoadingToComplete() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("div[ng-show='searchInProgress']"), "Loading"));
    }

    public int searchResultCount() {
        return driver.findElements(By.cssSelector(".dataRow")).stream().filter(e -> e.isDisplayed()).collect(Collectors.toList()).size();
    }
}
