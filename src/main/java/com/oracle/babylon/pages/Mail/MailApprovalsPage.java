package com.oracle.babylon.pages.Mail;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import static com.codeborne.selenide.Selenide.$;


/**
 * Class that contains common methods related to Mail Approvals
 * Author : sunilvve
 */
public class MailApprovalsPage extends Navigator {

    //Initializing the web elements
    private By configureApprBtn= By.xpath("//button[@title='Configure the approval requirements for different types of correspondences']");
    private By addButton=By.xpath("//button[@id='btnAdd']");
    private By approverSearchBox=By.xpath("//input[@id='lookupApprovers_query']");
    private By searchButton=By.xpath("//span[@id='lookupApprovers']//div[@class='bicon ic-search']");
    private By approverCheckBox=By.xpath("//input[@id='approver']");
    private By addApproverOkButton=By.xpath("//div[@class='uiButton-label' and text()='OK']");
    private By saveButton=By.xpath("//div[@class='uiButton-label' and text()='Save']");
    private By successMsgPanel=By.xpath("//ul[@class='messagePanel']");
    private By acceptButton=By.xpath("//img[@src='/html/Images/ic_appr_add.gif']");
    private By rejectButton=By.xpath("//img[@src='/html/Images/ic_appr_remv.gif']");


    /**
     * Function to create Approver
     * @param approverName
     */
    public Boolean createApprover(String approverName,String mailType) {
        getMenuSubmenu("Mail", "Mail Approvals");
        this.driver = commonMethods.switchToFrame(driver, "frameMain");
        commonMethods.waitForElement(driver,configureApprBtn);
        $(configureApprBtn).click();
        $(addButton).click();
        selectMailType(mailType);
        $(approverSearchBox).clear();
        $(approverSearchBox).sendKeys(approverName);
        $(searchButton).click();
        $(approverCheckBox).click();
        $(addApproverOkButton).click();
        commonMethods.waitForElement(driver,saveButton);
        $(saveButton).click();
        return $(successMsgPanel).isDisplayed();
    }

    /**
     * Function to select Mail Type
     * @param mailType
     */
    public void selectMailType(String mailType)
    {
        Select selectMailType=new Select(driver.findElement(By.xpath("//select[@id='selApprovableItems']")));
        selectMailType.selectByVisibleText(mailType);
    }

    /**
     * Function to Accept or Reject Mail from Approver
      * @param accept
     */
    public void acceptOrReject(boolean accept)
    {
        getMenuSubmenu("Mail", "Mail Approvals");
        this.driver = commonMethods.switchToFrame(driver, "frameMain");
        if(accept)
        {
            $(acceptButton).click();
        }
        else
        {
            $(rejectButton).click();
        }

    }



}
