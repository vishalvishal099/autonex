package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;


/**
 * Class that contains common methods related to Mails
 * Author : susgopal, sunilvve
 *
 */
public class MailPage extends Navigator {

    //Initializing the web elements
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By mailNoTextBox = By.id("rawQueryText");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By sendBtn = By.xpath("//button[@id='btnSend']");
    private By mailNo = By.xpath("//div[@class='mailHeader-numbers']//div[2]//div[2]");
    private By mailNoFromTable = By.xpath("//td[@class='column_documentNo']");
    private By primaryAttributeLeftPane = By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select");
    private By attributeAddButton = By.xpath("//button[@id='attributeBidi_PRIMARY_ATTRIBUTE_add']");
    private By correspondenceTypeId = By.id("Correspondence_correspondenceTypeID");

    //Reports
    private By reportsButton= By.xpath("//button[@class='auiMenuButton auiButton dropdown-toggle' and @title='Reports']");
    private By successMsg=By.xpath("//div[@class='auiMessage success']");
    private By closeButton=By.xpath("//button[@type='button' and text()='Close']");

    //Add Or Remove Column
    private By addOrRemoveButton=By.xpath("//button[@title='Choose the columns displayed in the search results']");
    private By addButton=By.xpath("//button[@title='Add item to list']");
    private By removeButton=By.xpath("//button[@title='Remove item from list']");
    private By okButton=By.xpath("//button[@id='ok']");
    private By resetButton=By.xpath("//button[@title='Cancel your current changes']");
    private By checkBoxSaveAsDefault= By.xpath("//label[@class='auiField-checkbox']//input[@class='auiField-input ng-pristine ng-untouched ng-valid ng-empty']");


    //Save search
    private By saveButton=By.id("savedSearches-saveButton");
    private By nameOfSearch=By.className("auiField-input ng-pristine ng-empty ng-invalid ng-invalid-required ng-valid-maxlength ng-touched");
    private By saveOkButton=By.xpath("//input[@id='ok']");

    //Edit Saved Search
    private By savedSearches=By.xpath("//div[@class='filter-container pull-left']");
    private By deleteSavedSearchButton=By.xpath("//button[@title='Delete this item']");
    private By confirmDeleteButton=By.xpath("//button[@name='actionButton']");
    private By newSearchName=By.xpath("auiField-input ng-pristine ng-valid ng-not-empty ng-valid-required ng-valid-maxlength ng-touched");

    //Advanced search
    private By advancedSearchButton=By.xpath("//button[@type='button' and text()='Advanced Search']");
    private By selectFilter=By.xpath("//select[@class='auiField-input ng-pristine ng-untouched ng-valid ng-empty']");


    /**
     * Function to search mails by the mail number search key
     *
     * @param mail_number
     */
    public void searchMailNumber(String mail_number) {
        $(mailNoTextBox).sendKeys(mail_number);
        $(searchBtn).click();
        commonMethods.waitForElementExplicitly(500);
        $(loadingIcon).should(disappear);
    }

    /**
     * Return the number of rows when we search for the mail
     *
     * @return
     */
    public int searchResultCount() {
        return driver.findElements(By.cssSelector(".dataRow")).stream().filter(e -> e.isDisplayed()).collect(Collectors.toList()).size();
    }

    /**
     * Function to select the mail type while composing the mail
     *
     * @param mailType input for correspondence type id
     */
    public void selectMailType(String mailType) {
        $(correspondenceTypeId).selectOption(mailType);
    }

    /**
     * Retrieve the mail using the mail number as the key
     *
     * @return the mail number of the
     */
    public String getMailNumber() {
        return driver.findElement(mailNoFromTable).getText();
    }

    /**
     * Function to click on Send button
     *
     * @return
     */
    public String send() {
        $(sendBtn).click();
        $(loadingIcon).should(disappear);
        String mailNumber = $(mailNo).getText();
        return mailNumber;
    }



    /**
     * Function to select the field attribute*
     *
     * @param attributeIdentifier key to be searched
     * @param value the value that needs to be selected
     */
    public void selectMailAttribute(String attributeIdentifier, String value) {
        driver = WebDriverRunner.getWebDriver();
        //Selecting attribute from the left panel to the right panel.Click on OK after it is done.
        $(By.xpath("//tr[td[label[contains(text(),'" + attributeIdentifier + "')]]]/td[@class='contentcell']/div")).click();
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
     * Function to Export Reports
     * @param reportType
     * @param typeOfExport
     * Example reportType=Export to Excel - Extended Report,Export to Excel - Standard Report,Export to Excel
     *          typeOfExport=Row per recipient,Row per mail
     */
    public void exportReports(String reportType,String typeOfExport)
    {
        commonMethods.waitForElement(driver,reportsButton);
        $(reportsButton).click();
        selectReportType(reportType);
        if(typeOfExport !=null)
        {
            $(By.xpath("//button[@type='button' and text()='"+typeOfExport+"']")).click();

        }

    }


    /**
     * Function to Select Report Type
     * @param reportType
     *
     */
    public void selectReportType(String reportType)
    {
        $(By.xpath("//ul[@class='dropdown-menu']//li//a[text()='"+reportType+"']")).click();

    }
    /**
     * Function to Add or Remove Columns
     * @param add
     * @param columnName
     * columnName:Name of the column which we need to Add or Remove
     *
     */
    public void addOrRemoveColumns(Boolean add,String columnName, Boolean saveAsDefault)
    {
        if(add)
        {
            $(addOrRemoveButton).click();
            $(By.xpath("//option[@label='"+columnName+"']")).click();
            $(addButton).click();
            $(okButton).click();


        }
        if(saveAsDefault)
        {
            $(checkBoxSaveAsDefault).click();
        }
        else
        {
            $(addOrRemoveButton).click();
            $(By.xpath("//div[@class='manual-sort']//option[@title='"+columnName+"']")).click();
            $(removeButton).click();
            $(okButton).click();

        }


    }
    /**
     * Function to save created search
     * @param sharingInfo with whom we can share created search
     * Sharinginfo is radio button to whom we need to share the created saved search
     */
    public String createSaveSearch(String sharingInfo)
    {
        $(saveButton).click();
        Faker faker=new Faker();
        String name=faker.app().name();
        $(nameOfSearch).sendKeys(name);
        $(By.xpath("//span[text()='"+sharingInfo+"']/..//input")).click();
        $(saveOkButton).click();
        return name;
    }


    /**
     * Function to Edit saved My searches
     * @param mySavedSearch
     * @param sharingInfo
     *
     */
    public void editSavedSearch(String mySavedSearch,String sharingInfo,String newSearchName)
    {
        $(savedSearches).click();
        $(By.xpath("//div[text()='"+mySavedSearch+"']/..//i[@title='Manage Saved Searches']")).click();
        if(newSearchName != null)
        {
            $(newSearchName).clear();
            $(newSearchName).sendKeys(newSearchName);
        }
        if (sharingInfo !=null)
        {
            $(By.xpath("//span[text()='" + sharingInfo + "']/..//input")).click();
        }
        $(saveOkButton).click();


    }

    /**
     * Function to Delete saved My searches
     * @param savedSearchName
     */
    public void deleteSavedSearch(String savedSearchName)
    {
        this.driver = commonMethods.switchToFrame(driver, "frameMain");
        $(savedSearches).click();
        $(By.xpath("//div[text()='"+savedSearchName+"']/..//i[@title='Manage Saved Searches']")).click();
        $(deleteSavedSearchButton).click();
        $(confirmDeleteButton).click();

    }

    /**
     * Function to Add Filter in Advanced search
     * @param filterName Name of the filter
     */
    public void addFilterToAdvancedSearch(String filterName)
    {

        $(advancedSearchButton).click();
        $(selectFilter).selectOption(filterName);

    }



}
