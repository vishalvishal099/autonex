package com.oracle.babylon.pages.Document;

import org.junit.Assert;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class that contains all the methods specific to the Document Register page
 */
public class DocumentRegisterPage extends DocumentPage{

    //Initialization of Web Elements
    private By transmitBtn = By.id("btnTransmitMenu");
    private By createTransmittal = By.xpath("//a[text()='Create a Transmittal']");
    private By selectIdsCheckbox = By.name("selectedIdsInPage");
    private By pageTitle = By.xpath("//h1[contains(text(),'Search -')]//span[text()='Document Register']");

    /**
     * Function to click the transmit button to create a transmittal
     */
    public void clickTransmitBtn(){
        $(transmitBtn).click();
    }

    /**
     * Method to navigate to Document and verify for the title of the page
     */
    public void navigateToDocumentRegisterAndVerify() {
        getMenuSubmenu("Documents", "Document Register");
        Assert.assertTrue(verifyPageTitle(pageTitle));
    }

    /**
     * Function to click the create transmittal link
     */
    public void clickCreateTransmittalLink(){
        $(createTransmittal).click();
    }

    public void selectDocAndNavigateToTransmittal(){
        $(selectIdsCheckbox).click();
        clickTransmitBtn();
        clickCreateTransmittalLink();
    }

}
