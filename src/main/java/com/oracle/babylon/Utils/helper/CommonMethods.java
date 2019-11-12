package com.oracle.babylon.Utils.helper;

import com.codeborne.selenide.WebDriverRunner;
import com.google.gson.Gson;
import com.oracle.babylon.pages.Admin.AdminHome;
import com.oracle.babylon.pages.Admin.AdminSearch;
import com.oracle.babylon.pages.Setup.EditPreferencesPage;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class containing all the common methods required for the framework
 * Author : susgopal
 */
public class CommonMethods {
    //Initializing the Web Elements
    By xoogleLabel = By.xpath("//td//label[text(),'Xoogle']");
    By attributeClickOk = By.xpath("//button[@id='attributePanel-commit' and @title='OK']");
    By primaryAttributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");
    By attributeAddButton = By.xpath("//button[@id='attributeBidi_PRIMARY_ATTRIBUTE_add']");

    /**
     * Takes the screenshot of the current browser window
     *
     * @param driver
     * @param screenshotName filename of the screenshot
     * @throws IOException
     */
    public static void takeSnapshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");
        FileUtils.copyFile(screenshotFile, destinationFile);
    }

    /**
     * Wait for a element to be visible for 5 seconds
     *
     * @param driver
     * @param by
     * @return
     */
    public WebDriver waitForElement(WebDriver driver, By by) {
        return waitForElement(driver, by, 5);
    }

    /**
     * Method to wait for the element to be visible for param seconds
     *
     * @param driver
     * @param by      attribute identifier
     * @param seconds
     * @return
     */
    public WebDriver waitForElement(WebDriver driver, By by, int seconds) {
        try {
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(driver, seconds);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        } catch (ElementNotVisibleException | InterruptedException e) {
            e.printStackTrace();
        }
        return driver;
    }

    /**
     * Method to switch the frame
     *
     * @param driver
     * @param frameId
     * @return
     */
    public WebDriver switchToFrame(WebDriver driver, String frameId) {
        driver.switchTo().frame(driver.findElement(By.id(frameId)));
        return driver;
    }

    /**
     * Function to switch the frame with the element attribute insted of the id
     * If we do not have a id attribute for the frame, we need to use other attributes
     * @param driver
     * @param by
     * @return
     */
    public WebDriver switchToFrame(WebDriver driver, By by) {
        driver.switchTo().frame(driver.findElement(by));
        return driver;
    }



    /**
     * Method to wait for a element explicitly for the millis provided
     *
     * @param millis
     */
    public void waitForElementExplicitly(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * public void waitForSearchLoadingToComplete(WebDriver driver, By by, String searchText) {
     * WebDriverWait wait = new WebDriverWait(driver, 30);
     * wait.until(ExpectedConditions.invisibilityOfElementWithText(by, searchText));
     * }
     */

    /**
     * Method to navigate to the sub menu
     *
     * @param driver
     * @param menuText    text for the menu item to be selected
     * @param subMenuText text for the sub menu item to be selected
     * @param frameText   select the frame text to be switched to
     * @return the driver
     */
    public WebDriver selectSubMenu(WebDriver driver, String menuText, String subMenuText, String frameText) {
        Navigator navigator = new Navigator();
        driver.switchTo().defaultContent();
        navigator.getMenuSubmenu(menuText, subMenuText);
        return driver;
    }

    /**
     * Method to navigate to the sub menu in the Admin tool
     *
     * @param driver
     * @param menuText    text for the menu item to be selected
     * @param subMenuText text for the sub menu item to be selected
     * @param frameText   select the frame text to be switched to
     * @return the driver
     */
    public WebDriver selectSubMenuAdmin(WebDriver driver, String menuText, String subMenuText, String frameText) {
        Navigator navigator = new Navigator();
        driver.switchTo().defaultContent();
        navigator.getMenuSubmenuAdmin(menuText, subMenuText);
        return driver;
    }


    /**
     * Method to convert the API response to the string that can be parsed
     *
     * @param entity
     * @return
     * @throws IOException
     */
    public static String entityToString(HttpEntity entity) throws IOException {
        InputStream is = entity.getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        //Using string builder to read the entity and convert to string
        StringBuilder str = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                str.append(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {

            }
        }
        return str.toString();
    }

    public static String convertMaptoJsonString(Object src) {
        return new Gson().toJson(src);
    }

    /**
     * Function to search the project and return the project id
     *
     * @param driver
     * @param projectName
     * @return project id
     */
    public String searchProject(WebDriver driver, String projectName) throws InterruptedException {
        Navigator navigator = new Navigator();
        navigator.loginAsAdmin();
        CommonMethods commonMethods = new CommonMethods();
        driver = commonMethods.selectSubMenu(driver, "Setup","Search", "frameMain");
        AdminSearch adminSearch = new AdminSearch(driver);
        return adminSearch.returnResultId(projectName);
    }

    /**
     * Function to convert the json string to xml string. Helps in building api request body for Aconex API.
     * Standard format required is XML based
     *
     * @param jsonString
     * @return
     */
    public String convertJsonStringToXMLString(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return XML.toString(jsonObject);
    }

    /**
     * Function to convert UI table from the search to a Hash Map
     * Single result ui table
     *
     * @param driver
     * @param tableElement table identifier
     * @param rowElement   row identifier
     * @return
     */
    public List<Map<String, String>> convertUITableToHashMap(WebDriver driver, By tableElement, By rowElement) {

        List<String> columns = returnColumnHeaders(driver, tableElement);
        List<List<String>> rowsList = returnRows(driver, rowElement);
        Map<String, String> tableHashMap = new HashMap<>();
        List<Map<String, String>> listMap = new ArrayList<>();
        for (List<String> rows : rowsList) {
            int i = 1;
            for (String columnHeader : columns) {
                tableHashMap.put(columnHeader, rows.get(i));
                i++;
            }


            listMap.add(tableHashMap);
        }
        return listMap;

    }

    /**
     * Function to fetch the column headers and store it in a list
     *
     * @param driver
     * @param tableElement table identifier
     * @return list of column headers
     */
    public List<String> returnColumnHeaders(WebDriver driver, By tableElement) {
        List<WebElement> listOfColumns = driver.findElement(tableElement).findElements(By.xpath("//thead//tr//th"));
        List<String> columnHeaders = new ArrayList<String>();
        for (WebElement element : listOfColumns) {
            if (!element.getText().equals("")) {
                columnHeaders.add(element.getText());
            }
        }
        return columnHeaders;
    }

    /**
     * Method to return the list of list of rows.
     * Used when multiple results are visible in the ui
     *
     * @param driver
     * @param rowIdentifier
     * @return
     */
    public List<List<String>> returnRows(WebDriver driver, By rowIdentifier) {
        List<WebElement> listOfRows = driver.findElements(rowIdentifier);
        String identifier = rowIdentifier.toString().substring(rowIdentifier.toString().indexOf('/'));
        List<String> individualCellData = new ArrayList<String>();
        List<List<String>> rowCellData = new ArrayList<List<String>>();
        for (int i = 0; i < listOfRows.size(); i++) {
            //Storing individual cell of a row in a list
            List<WebElement> cellElements = driver.findElements(By.xpath(identifier + "//td"));
            for (WebElement individualCellElements : cellElements) {
                individualCellData.add(individualCellElements.getText());
            }
            //Storing individual row in a list
            rowCellData.add(individualCellData);
        }
        return rowCellData;
    }


    /**
     * Function to select the field attribute*
     *
     * @param attributeIdentifier key to be searched
     * @param value the value that needs to be selected
     */
    public void selectAttribute(String attributeIdentifier, String value) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        //Selecting attribute from the left panel to the right panel.Click on OK after it is done.
        $(By.xpath("//tr[td[label[contains(text(),'" + attributeIdentifier + "')]]]/td[@class='contentcell']/div")).click();
        CommonMethods commonMethods = new CommonMethods();
        driver = commonMethods.waitForElement(driver, (primaryAttributeLeftPane));
        Select select = new Select(driver.findElement(primaryAttributeLeftPane));
        driver = commonMethods.waitForElement(driver, By.xpath(".//option[text()='" + value + "']"));
        select.selectByVisibleText(value);
        driver = commonMethods.waitForElement(driver, attributeAddButton);
        $(attributeAddButton).click();
        driver = commonMethods.waitForElement(driver, By.xpath("//button[@id='attributePanel-commit' and @title='OK']"));
        selectAttributeClickOK();
    }

    /**
     * Function to select any attribute already present in the server
     * @param attributeIdentifier
     */
    public void selectDefaultAttribute(String attributeIdentifier){
        WebDriver driver = WebDriverRunner.getWebDriver();
        //Selecting attribute from the left panel to the right panel.Click on OK after it is done.
        $(By.xpath("//tr[td[label[contains(text(),'" + attributeIdentifier + "')]]]/td[@class='contentcell']/div")).click();
        CommonMethods commonMethods = new CommonMethods();
        driver = commonMethods.waitForElement(driver, (primaryAttributeLeftPane));
        Select select = new Select(driver.findElement(primaryAttributeLeftPane));
        select.selectByIndex(0);
        $(attributeAddButton).click();
        commonMethods.waitForElement(driver, By.xpath("//button[@id='attributePanel-commit' and @title='OK']"));
        selectAttributeClickOK();
    }

    /*
    Method to select the ok button when selecting the attribute value
     */
    public void selectAttributeClickOK() {
        $(attributeClickOk).click();
    }

    /**
     * Function to validate if the attribute with a certain value is present
     *
     * @param by        element to validate
     * @param attribute attribute to validate
     * @return the status of the validation
     */
    public boolean isAttributePresent(By by, String attribute) {
        String value = $(by).getAttribute(attribute);
        if (value!=null && value.equals("true")) {
            return true;
        }
        return false;
    }

    /**
     * Function to return the value of an attribute from a Web Element
     * @param by
     * @param attribute
     * @return
     */
    public String returnAttributeValue(By by, String attribute){
        return $(by).getAttribute(attribute);
    }


    /**
     * Function to select the link that we need to change the settings for
     *
     * @param linkText
     */
    public void clickLinkToChange(By pageHeader, String linkText) {
        $(pageHeader).isDisplayed();
        $(By.xpath("//a[text()='" + linkText + "']")).click();

    }



}


