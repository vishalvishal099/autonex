package com.oracle.babylon.pages.Mail;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains common methods related to Mail SearchPage
 * Author : sunilvve
 */
public class SearchMailPage extends Navigator {

    //Initializing the web elements
    private By reportsButton= By.xpath("//button[@class='auiMenuButton auiButton dropdown-toggle' and @title='Reports']");
    private By successMsg=By.xpath("//div[@class='auiMessage success']");
    private By closeButton=By.xpath("//button[@type='button' and text()='Close']");

    //Add Or Remove Column
    private By addOrRemoveButton=By.xpath("//button[@title='Choose the columns displayed in the search results']");
    private By addButton=By.xpath("//button[@title='Add item to list']");
    private By okButton=By.xpath("//button[@id='ok']");
    private By removeButton=By.xpath("//button[@title='Remove item from list']");
    private By resetButton=By.xpath("//button[@title='Cancel your current changes']");

    //Save search
    private By saveButton=By.id("savedSearches-saveButton");
    private By nameOfSearch=By.className("auiField-input ng-pristine ng-empty ng-invalid ng-invalid-required ng-valid-maxlength ng-touched");
    private By saveOkButton=By.xpath("//input[@id='ok']");


    /**
     * Function to Export Reports
     * @param reportType
     * @param typeOfExport
     * Example reportType=Export to Excel - Extended Report,Export to Excel - Standard Report,Export to Excel
     *          typeOfExport=Row per recipient,Row per mail
     */
    public Boolean exportReports(String reportType,String typeOfExport)
    {
        if(typeOfExport !=null)
        {
            getMenuSubmenu("Mail", "Inbox");
            this.driver = commonMethods.switchToFrame(driver, "frameMain");
            commonMethods.waitForElement(driver,reportsButton);
            $(reportsButton).click();
            selectReportType(reportType);
            $(By.xpath("//button[@type='button' and text()='"+typeOfExport+"']")).click();
            return $(successMsg).isDisplayed();
        }
        else
        {
            getMenuSubmenu("Mail", "Inbox");
            this.driver = commonMethods.switchToFrame(driver, "frameMain");
            $(reportsButton).click();
            selectReportType(reportType);
           return $(successMsg).isDisplayed();
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
    public void addOrRemoveColumns(boolean add,String columnName)
    {
        getMenuSubmenu("Mail", "Inbox");
        this.driver = commonMethods.switchToFrame(driver, "frameMain");
        if(add)
        {
            $(addOrRemoveButton).click();
            $(By.xpath("//option[@label='"+columnName+"']")).click();
            $(addButton).click();
            $(okButton).click();

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
     * @param sharingInfo
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



}
