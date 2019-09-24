package com.oracle.babylon.Utils.helper;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class Navigator {
    private User user = null;
    private DataStore dataStore = new DataStore();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private WebDriver driver;

    public Navigator() {
        driver = WebDriverRunner.getWebDriver();
    }

    public <P> void visit(P page, String username, Consumer<P> block) {
        user = dataStore.getUser(username);
        visit(page, user.getUserName());
        block.accept(page);
    }

    public <P> void visit(P page, String username) {
        switchTo().defaultContent();
        if ($(By.xpath("//span[@class='nav-userAvatar']")).isDisplayed()) {
            logout();
            waitForElement();
            open(configFileReader.getApplicationUrl());
        } else {
            open(configFileReader.getApplicationUrl());
        }
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


    public WebElement menuFinder(String css, String text) {
        return driver.findElements(By.cssSelector(css)).stream()
                .filter(e -> e.getText().equalsIgnoreCase(text) && e.isDisplayed() && e.isEnabled())
                .findFirst()
                .get();
    }

    public void verifyUserPresent() {
        $(By.xpath("//span[@class='nav-userDetails']")).shouldHave(text(user.getFullName()));
    }

    public void verifyUserNotPresent() {
        $(By.xpath("//span[@class='nav-userDetails']")).shouldNot(text(user.getFullName()));
    }

    public void verifyLoginFailed() {
        $(By.xpath("//li[@class='message warning']//div[text()='Your login name or password is incorrect. Check that caps lock is not on.']")).shouldHave(text("Your login name or password is incorrect. Check that caps lock is not on."));
    }

    public void waitForElement() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        $(By.xpath("//span[@class='nav-user-chevron']")).click();
        waitForElement();
        $(By.xpath("//a[@id='logoff'] ")).click();
        $(".loading_progress").should(disappear);
    }
}
