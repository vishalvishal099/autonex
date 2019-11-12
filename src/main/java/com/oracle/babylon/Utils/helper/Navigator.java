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
    private By avatar = By.xpath("//span[@class='nav-userAvatar']");
    private By usernameTxtBox = By.id("userName");
    private By passwordTxtBox = By.id("password");
    private By loginBtn = By.id("login");
    private By projectChangerSelect = By.id("projectChanger-name");
    private By userDetails = By.xpath("//span[@class='nav-userDetails']");
    private By loadingProgressIcon = By.cssSelector(".loading_progress");
    private By loginFailureMessage = By.xpath("//li[@class='message warning']//div[text()='Your login name or password is incorrect. Check that caps lock is not on.']");
    private By logOutNavigator = By.xpath("//span[@class='nav-userDetails']");
    private By logOffBtn = By.xpath("//a[@id='logoff']");
    private By registerLink = By.xpath("//a[text()='Register']");



    public Navigator() {
        driver = WebDriverRunner.getWebDriver();
    }

    public <P> void loginAsUser(P page, String username, Consumer<P> block) {
        user = dataStore.getUser(username);
        loginAsUser(user);
        block.accept(page);
    }

    public <P> void loginAsUser(User user) {
        switchTo().defaultContent();
        if ($(avatar).isDisplayed()) {
            logout();
            CommonMethods commonMethods = new CommonMethods();
            commonMethods.waitForElementExplicitly(2000);
            open(configFileReader.getApplicationUrl());
        } else {
            open(configFileReader.getApplicationUrl());
        }
        enterCreds(user.getUserName(), user.getPassword().toString());

        selectProject(user.getProject());
    }

    public <P> void loginToServer(String userName, String password, String projectName) {
        switchTo().defaultContent();
        CommonMethods commonMethods = new CommonMethods();
        if ($(avatar).isDisplayed()) {
            logout();

            commonMethods.waitForElementExplicitly(2000);
            open(configFileReader.getApplicationUrl());
        } else {
            open(configFileReader.getApplicationUrl());
        }
        enterCreds(userName, password);
        commonMethods.waitForElementExplicitly(2000);
        if($(projectChangerSelect).isDisplayed()){
            selectProject(projectName);
        } else{
            System.out.println("No Projects available for the user");
        }

    }

    public <P> void loginAsAdmin(P page, Consumer<P> block) {

        loginAsAdmin();
        block.accept(page);
    }

    public <P> void loginAsAdmin() {
        switchTo().defaultContent();
        if ($(avatar).isDisplayed()) {
            logout();
            CommonMethods commonMethods = new CommonMethods();
            commonMethods.waitForElementExplicitly(2000);
            open(configFileReader.getApplicationUrl());
        } else {
            open(configFileReader.getApplicationUrl());
        }
        enterCreds(configFileReader.getAdminUsername(), configFileReader.getAdminPassword());
    }

    public <P> void on(P page, Consumer<P> block) {
        block.accept(page);
    }

    public void as(String username) {
        open(configFileReader.getApplicationUrl());
        user = dataStore.getUser(username);
        enterCreds(user.getUserName(), user.getPassword().toString());
    }



    public void enterCreds(String username, String password){


        $(usernameTxtBox).setValue(username);
        $(passwordTxtBox).setValue(password);
        $(loginBtn).click();
        $(".loading_progress").should(disappear);
    }

    public void selectProject(String projectName) {
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.waitForElement(driver, projectChangerSelect, 5);
        if ($(projectChangerSelect).text() == (projectName)) {
        } else
            $(projectChangerSelect).click();
        $(By.xpath("//div[@class='projectChanger-listItem']//span[text()='" + projectName + "']")).click();
    }

    public void getMenuSubmenu(String menu, String submenu) {
        $(By.xpath("//button[@class='uiButton navBarButton']//div[text()='" + menu + "']")).click();
        $(By.xpath("//div[@class='navBarPanel-menuItem' and contains(text(),'" + submenu + "' )]")).click();
        $(loadingProgressIcon).should(disappear);
    }

    public void getMenuSubmenuAdmin(String menu, String submenu) {
        $(By.xpath("//button[@class='uiButton navBarButton active']//div[text()='" + menu + "']")).click();
        $(By.xpath("//div[@class='navBarPanel-menuItem' and contains(text(),'" + submenu + "' )]")).click();
        $(loadingProgressIcon).should(disappear);
    }


    public WebElement menuFinder(String css, String text) {
        return driver.findElements(By.cssSelector(css)).stream()
                .filter(e -> e.getText().equalsIgnoreCase(text) && e.isDisplayed() && e.isEnabled())
                .findFirst()
                .get();
    }

    public void verifyUserPresent() {
        $(userDetails).shouldHave(text(user.getFullName()));
    }

    public void verifyUserNotPresent() {
        $(userDetails).shouldNot(text(user.getFullName()));
    }

    public void verifyLoginFailed() {
        $(loginFailureMessage).shouldHave(text("Your login name or password is incorrect. Check that caps lock is not on."));
    }


    /**
     * Function to logout from the server.
     */
    public void logout() {

        $(logOutNavigator).click();
        CommonMethods commonMethods = new CommonMethods();

        driver = commonMethods.waitForElement(driver, logOffBtn);
        $(logOffBtn).click();
        $(loadingProgressIcon).should(disappear);
    }

    /**
     * Function to click on Register link to create a organization
     */
    public void clickRegisterLink(){
        $(registerLink).click();
    }

    public void openAconexUrl(){
        open(configFileReader.getApplicationUrl());
    }
}
