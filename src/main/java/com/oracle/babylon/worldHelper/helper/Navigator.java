package com.oracle.babylon.worldHelper.helper;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.worldHelper.Setup.DataStore.DataStore;
import com.oracle.babylon.worldHelper.Setup.DataStore.User;
import com.oracle.babylon.worldHelper.Setup.utils.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Navigator {
    User user = null;
    DataStore dataStore = new DataStore();
    ConfigFileReader configFileReader = new ConfigFileReader();
    public WebDriver driver;

    public Navigator() {
        driver = WebDriverRunner.getWebDriver();
    }

    public <P> void visit(P page, String username, Consumer<P> block) {
        user = dataStore.getUser(username);
        visit(page, user.getUserName());
        block.accept(page);
    }

    public <P> void visit(P page, String username) {
        open(configFileReader.getApplicationUrl());
        login(user);
        selectProject();
    }

    public <P> void on(P page, Consumer<P> block) {
        block.accept(page);
    }

    public void as(String username) {
        open(configFileReader.getApplicationUrl());
        user = dataStore.getUser(username);
        login(user);
    }

    public void login(User user) {
        $(By.id("userName")).setValue(user.getUserName());
        $(By.id("password")).setValue(user.getPassword());
        $(By.id("login")).click();
        $(".loading_progress").should(disappear); // Waits until element disappears
    }

    public void selectProject() {
        if ($(By.id("projectChanger-name")).text() == (user.getProject().toString())) {
        } else
            $(By.id("projectChanger-name")).click();
        $(By.xpath("//div[@class='projectChanger-listItem']//span[text()='" + user.getProject() + "']")).click();
    }

    public void switchToFrame(String frameId) {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.id(frameId)));
    }

    public void getMenuSubmenu(String menu, String submenu) {
        $(By.xpath("//button[@class='uiButton navBarButton']//div[text()='" + menu + "']")).click();
        $(By.xpath("//div[@class='navBarPanel-menuItem' and contains(text(),'" + submenu + "' )]")).click();
        $(".loading_progress").should(disappear);
    }

    public void clickMenuSubMenu(String menu, String submenu) {
        menuFinder("button.uiMenuButton", menu).click();
        menuFinder("div.navBarPanel-menuItem", submenu).click();
    }

    public WebElement menuFinder(String css, String text) {
        return driver.findElements(By.cssSelector(css)).stream()
                .filter(e -> e.getText().equalsIgnoreCase(text) && e.isDisplayed() && e.isEnabled())
                .findFirst()
                .get();
    }

    public void verifyUserPresent() {
        $(By.xpath("//span[@class='nav-userDetails']")).shouldHave(text(user.getFullName().toString()));
    }

    public void verifyUserNotPresent() {
        $(By.xpath("//span[@class='nav-userDetails']")).shouldNot(text(user.getFullName().toString()));
    }

    public void verifyLoginFailed() {
        $(By.xpath("//li[@class='message warning']//div[text()='Your login name or password is incorrect. Check that caps lock is not on.']")).shouldHave(text("Your login name or password is incorrect. Check that caps lock is not on."));
    }
}
