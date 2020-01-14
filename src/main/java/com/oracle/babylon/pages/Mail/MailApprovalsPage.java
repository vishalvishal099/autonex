package com.oracle.babylon.pages.Mail;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;


/**
 * Class that contains common methods related to Mail Approvals
 * Author : sunilvve
 */
public class MailApprovalsPage extends Navigator {

    //Initializing the web elements
    private By configureApproverBtn= By.xpath("//button[@title='Configure the approval requirements for different types of correspondences']");
    private By addButton=By.xpath("//button[@id='btnAdd']");
    private By approverSearchBox=By.xpath("//input[@id='lookupApprovers_query']");
    private By searchButton=By.xpath("//span[@id='lookupApprovers']//div[@class='bicon ic-search']");
    private By approverCheckBox=By.xpath("//input[@id='approver']");
    private By addApproverOkButton=By.xpath("//div[@class='uiButton-label' and text()='OK']");
    private By saveButton=By.xpath("//div[@class='uiButton-label' and text()='Save']");
    private By successMsgPanel=By.xpath("//ul[@class='messagePanel']");
    private By acceptButton=By.xpath("//img[@src='/html/Images/ic_appr_add.gif']");
    private By rejectButton=By.xpath("//img[@src='/html/Images/ic_appr_remv.gif']");

    private By tabOrganiation=By.xpath("//li[@id='organization']");

    //Multiple Mail Type
    private By linkMultipleMailType=By.xpath("//a[@id='lnkMailTypeSingle']");
    private By addMailTypeButton=By.xpath("//button[@id='btnbidi_addApprovableItem_ADD']");

    //Tenders
    private By addLinkForTenders=By.xpath("//table[@id='tblApprovabieItemList']//tbody[@id='tbdNoTenderItems']//a");

    //Mail
    private By addLinkForMail= By.xpath("//table[@id='tblApprovabieItemList']//tbody[@id='tbdNoMailItems']//a");



    /**
     * Function to create Approver For Organization or Peoject level
     * @param organization
     */
    public void selectLevelOfApprover(Boolean organization)
    {
        if(organization)
        {
            $(tabOrganiation).click();
        }

    }

    /**
     * Function to create Approver for Tenders
     * @param approverName
     * @param single
     * @param mailTypes
     *
     */
    public void createApproverForTenders(String approverName,List<String>mailTypes,String single)
    {
        $(addLinkForTenders).click();
        if(single !=null) {
            addSingleMailType(mailTypes);
        }
        else {
            addMultipleMailTypes(mailTypes);
        }
        addApprover(approverName);
    }

    /**
     * Function to create Approver For Mail
     * @param approverName
     * @param mailTypes
     * @param single
     *
     */
    public void createApproverForMail(String approverName,List<String> mailTypes,String single)
    {
        $(addLinkForMail).click();
        if(single !=null) {
            addSingleMailType(mailTypes);
        }
        else {
            addMultipleMailTypes(mailTypes);
        }
        addApprover(approverName);

    }
    /**
     * Function to create Approver
     * @param approverName
     * @param single
     * @param mailTypes
     */
    public void createApproverWithAdd(String approverName,List<String> mailTypes,String single) {
        $(addButton).click();
        if (single !=null) {
            addSingleMailType(mailTypes);
        } else {
            addMultipleMailTypes(mailTypes);
        }
        addApprover(approverName);
    }
    /**
     * Function to Add Single Mail Type
     * @param mailTypes
     */
    public void addSingleMailType(List<String> mailTypes)
    {
        for(int i=0;i<=mailTypes.size()-1;i++) {
            selectSingleMailType(mailTypes.get(i));

        }
    }

    /**
     * Function to Add Multiple Mail Types
     * @param mailTypes
     *
     */
    public void addMultipleMailTypes(List<String> mailTypes)
    {
        $(linkMultipleMailType).click();
        for(int i=0;i<=mailTypes.size()-1;i++) {
            selectMultiMailType(mailTypes.get(i));
            $(addMailTypeButton).click();

        }

    }


    /**
     * Add Aprrover
     * @param arroverName
     *
     */
    public void addApprover(String arroverName)
    {
        $(approverSearchBox).clear();
        $(approverSearchBox).sendKeys(arroverName);
        $(searchButton).click();
        $(approverCheckBox).click();
        $(addApproverOkButton).click();
        commonMethods.waitForElement(driver,saveButton);
        $(saveButton).click();
    }

    /**
     * Function to select Mail Type
     * @param mailType
     */
    public void selectSingleMailType(String mailType)
    {
        Select selectMailType=new Select(driver.findElement(By.xpath("//select[@id='selApprovableItems']")));
        selectMailType.selectByVisibleText(mailType);
    }


    /**
     *
     */
    public void selectMultiMailType(String mailTYpe)
    {
        Select selectMultiMail=new Select(driver.findElement(By.xpath("//select[@id='bidi_addApprovableItem_AVAIL']")));
        selectMultiMail.selectByVisibleText(mailTYpe);
    }
    /**
     * Function to Accept or Reject Mail from Approver
      * @param accept
     */
    public void acceptOrReject(boolean accept)
    {
        getMenuSubmenu("Mail", "Mail Approvals");
        this.driver = commonMethods.switchToFrame(driver, "frameMain");
        if(accept) {
            $(acceptButton).click();
        }
        else {
            $(rejectButton).click();
        }

    }



}
