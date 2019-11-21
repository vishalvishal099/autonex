package com.oracle.babylon.pages.Admin;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class file to contain all the method related to the Admin Xoogle Search Page
 * Author : susgopal
 */
public class AdminSearch extends Navigator {

    //Initialization the web elements
    private By xoogleLabel = By.xpath("//table[@class='formTable']//td//label[text()='Xoogle']");
    private By searchKeywords = By.name("SRCH_KEYWORDS");

    public AdminSearch(WebDriver driver){
       this.driver = driver;
    }

    /**
     * Returns the id for a single element search. Matches the name of the result obtained, retrieves the associated id
     * @param searchText
     * @return
     */
    public String returnResultId(String searchText){
        search(searchText);
        By searchResult = By.xpath("//td//a[text()='"+ searchText+ "'] ");
        String elementInfo = $(searchResult).parent().getText();
        return elementInfo.substring(elementInfo.indexOf('[')+1, elementInfo.indexOf(']'));

    }

    /**
     * Click on the search results. Some test scenarios need the user to navigate to the information of the results
     * @param searchText Text to be searched and specified in the Search text box
     * @param returnResultText Text/ID constant that we use to click on the returned results
     */
    public void clickSearchResults(String searchText, String returnResultText){
        search(searchText);
        By searchResult = By.xpath("//td//a[text()='"+ returnResultText+ "'] ");
        $(searchResult).click();
    }

    /**
     * Search using a key to retrieve associated rows
     */
    public void search(String searchKey){
        driver = commonMethods.switchToFrame(driver, "frameMain");
        commonMethods.waitForElement(driver, xoogleLabel, 5);
        $(searchKeywords).sendKeys(searchKey);
        $(searchKeywords).pressEnter();
    }
}
