package com.oracle.babylon.Utils.helper;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Consumer;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/**
 * Base class for all page files
 * Author: vsingsi
 * Edited by : susgopal
 */
public class Navigator {
    protected User user = new User();
    protected DataStore dataStore = new DataStore();
    protected ConfigFileReader configFileReader = new ConfigFileReader();
    protected WebDriver driver = null;
    protected CommonMethods commonMethods = new CommonMethods();
    protected APIRequest apiRequest = new APIRequest();
    protected DataSetup dataSetup = new DataSetup();
    protected Map<String, String> projectMap = null;
    protected Map<String, String> userMap = null;
    protected Map<String, Map<String, String>> jsonMapOfMap = null;


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
    private By attributeClickOk = By.xpath("//button[@id='attributePanel-commit' and @title='OK']");
    protected By header = By.xpath("//h1");
    protected String filePath = null;


    public Navigator() {
        driver = WebDriverRunner.getWebDriver();
        filePath = configFileReader.returnUserDataJsonFilePath();
    }

    /**
     * Method to login using the user id from the feature file. One of the overloaded methods
     *
     * @param page
     * @param username user id from the feature file
     * @param block
     * @param <P>
     */
    public <P> void loginAsUser(P page, String username, Consumer<P> block) {
        user = dataStore.getUser(username);
        loginAsUser(user);
        block.accept(page);
    }

    /**
     * Method to login using the details from userData.json. We convert json data to user object and use it.
     *
     * @param page
     * @param userId
     * @param block
     * @param <P>
     */
    public <P> void loginAsUser(P page, String userId, String filePath, Consumer<P> block) {
        //Parse the json to retrieve the essential information and store it in user object
        jsonMapOfMap = dataSetup.loadJsonDataToMap(filePath);
        char numberChar = userId.charAt(userId.length() - 1);
        String projectId = "project" + numberChar;
        projectMap = jsonMapOfMap.get(projectId);
        userMap = jsonMapOfMap.get(userId);
        user.setProject(projectMap.get("projectname"));
        user.setUserName(userMap.get("username"));
        user.setPassword(userMap.get("password"));
        user.setFullName(userMap.get("fullname"));

        loginAsUser(user);
        block.accept(page);
    }

    /**
     * Method to login to aconex as a admin user
     *
     * @param page
     * @param block
     * @param <P>
     */
    public <P> void loginAsUser(P page, Consumer<P> block) {
        user.setUserName(configFileReader.getAdminUsername());
        user.setPassword(configFileReader.getPassword());

        loginAsUser(user);
        block.accept(page);
    }


    public <P> void loginAsUser(User user) {
        switchTo().defaultContent();
        if ($(avatar).isDisplayed()) {
            logout();
            commonMethods.waitForElementExplicitly(2000);
            openAconexUrl();
        } else {
            openAconexUrl();
        }
        enterCreds(user.getUserName(), user.getPassword());
        commonMethods.waitForElementExplicitly(3000);
        selectProject(user.getProject());
    }

    public <P> void on(P page, Consumer<P> block) {
        block.accept(page);
    }

    public void as(String tablename) {
        open(configFileReader.getApplicationUrl());
        user = dataStore.getUser(tablename);
        enterCreds(user.getUserName(), user.getPassword().toString());
    }


    public void enterCreds(String username, String password) {
        $(usernameTxtBox).setValue(username);
        $(passwordTxtBox).setValue(password);
        $(loginBtn).click();
        $(".loading_progress").should(disappear);
    }

    public void selectProject(String projectName) {
        commonMethods.waitForElementExplicitly(2000);
        if($(projectChangerSelect).isDisplayed()) {
            if ($(projectChangerSelect).text() == (projectName)) {
            } else
                $(projectChangerSelect).click();
            $(By.xpath("//div[@class='projectChanger-listItem']//span[text()='" + projectName + "']")).click();
        } else {
            System.out.println("No projects available to select");
        }
    }

    public WebDriver getMenuSubmenu(String menu, String submenu) {
        driver = WebDriverRunner.getWebDriver();
        driver.switchTo().defaultContent();
        $(By.xpath("//button[@class='uiButton navBarButton']//div[text()='" + menu + "']")).click();
        $(By.xpath("//div[@class='navBarPanel-menuItem' and contains(text(),'" + submenu + "' )]")).click();
        $(loadingProgressIcon).should(disappear);
        return driver;
    }

    public WebElement menuFinder(String css, String text) {
        return driver.findElements(By.cssSelector(css)).stream()
                .filter(e -> e.getText().equalsIgnoreCase(text) && e.isDisplayed() && e.isEnabled())
                .findFirst()
                .get();
    }

    public void verifyUserPresent(String fullname) {
        $(userDetails).shouldHave(text(fullname));
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
        driver = commonMethods.waitForElement(driver, logOffBtn);
        $(logOffBtn).click();
        $(loadingProgressIcon).should(disappear);
    }

    /**
     * Function to click on Register link to create a organization
     */
    public void clickRegisterLink() {
        $(registerLink).click();
    }

    public void openAconexUrl() {
        open(configFileReader.getApplicationUrl());
    }

    /*
   Method to select the ok button when selecting the attribute value
    */
    public void selectAttributeClickOK() {
        $(attributeClickOk).click();
    }

    public Map<String, String> returnProjectMap(){
        return projectMap;
    }

    /**
     * Method to verify the element is displayed
     * @param by
     * @return
     */
    public boolean verifyPageTitle(By by){
        commonMethods.switchToFrame(driver, "frameMain");
        Boolean isDisplayed =  $(by).isDisplayed();
        switchTo().defaultContent();
        return isDisplayed;
    }

    /**
     * Method to verify the text of the element
     * @param pageTitle
     * @return
     */
    public boolean verifyPageTitle(String pageTitle){
        commonMethods.switchToFrame(driver, "frameMain");
        String headerName = $(header).text();
        switchTo().defaultContent();
        return (headerName.contains(pageTitle));
    }
}
