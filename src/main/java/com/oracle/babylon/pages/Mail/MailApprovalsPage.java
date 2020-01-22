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
    private By configureApproverBtn = By.xpath("//button[@title='Configure the approval requirements for different types of correspondences']");
    private By addBtn = By.xpath("//button[@id='btnAdd']");
    private By approverSearchBox = By.xpath("//input[@id='lookupApprovers_query']");
    private By searchBtn = By.xpath("//span[@id='lookupApprovers']//div[@class='bicon ic-search']");
    private By approverCheckBox = By.xpath("//input[@id='approver']");
    private By createdByCheckBox=By.xpath("//input[@id='orgAuthor']");
    private By addApproverOkBtn = By.xpath("//div[@class='uiButton-label' and text()='OK']");
    private By saveBtn = By.xpath("//div[@class='uiButton-label' and text()='Save']");
    private By successMsgPanel = By.xpath("//ul[@class='messagePanel']");
    private By acceptBtn = By.xpath("//img[@src='/html/Images/ic_appr_add.gif']");
    private By rejectBtn = By.xpath("//img[@src='/html/Images/ic_appr_remv.gif']");
    private By tabOrganization = By.xpath("//li[@id='organization']");
    private By linkMultipleMailType = By.xpath("//a[@id='lnkMailTypeSingle']");
    private By addMailTypeBtn = By.xpath("//button[@id='btnbidi_addApprovableItem_ADD']");
    private By addLinkForTenders = By.xpath("//table[@id='tblApprovabieItemList']//tbody[@id='tbdNoTenderItems']//a");
    private By addLinkForMail = By.xpath("//table[@id='tblApprovabieItemList']//tbody[@id='tbdNoMailItems']//a");


    /**
     * Function to create Approver For Organization or Peoject level
     *
     * @param organization
     */
    public void selectLevelOfApprover(Boolean organization) {
        if (organization) {

            $(tabOrganization).click();
        }

    }

    //Common method configuremailapprover and call it in the following 3 methods

    /**
     * Function to Create Approver
     *
     * @param approverName
     * @param mailTypes
     * @param single
     */
    public void configureMailApprover(String approverName, List<String> mailTypes, String single,String approver) {
        if (single != null) {
            addSingleMailType(mailTypes);
        } else {
            addMultipleMailTypes(mailTypes);
        }
        addApprover(approverName,approver);
        $(saveBtn).click();

    }

    /**
     * Function to create Approver for Tenders
     *
     * @param approverName
     * @param single
     * @param mailTypes
     */
    public void createApproverForTenders(String approverName, List<String> mailTypes, String single,String approver) {
        $(addLinkForTenders).click();
        configureMailApprover(approverName, mailTypes, single,approver);
    }

    /**
     * Function to create Approver For Mail
     *
     * @param approverName
     * @param mailTypes
     * @param single
     */
    public void createApproverForMail(String approverName, List<String> mailTypes, String single,String approver) {
        $(addLinkForMail).click();
        configureMailApprover(approverName, mailTypes, single,approver);
    }

    /**
     * Function to create Approver
     *
     * @param approverName
     * @param single
     * @param mailTypes
     */
    public void createApproverWithAdd(String approverName, List<String> mailTypes, String single,String approver) {
        $(addBtn).click();
        configureMailApprover(approverName, mailTypes, single,approver);
    }

    /**
     * Function to Add Single Mail Type
     *
     * @param mailTypes
     */
    public void addSingleMailType(List<String> mailTypes) {
        selectSingleMailType(mailTypes.get(0));
    }

    /**
     * Function to Add Multiple Mail Types
     *
     * @param mailTypes
     */
    public void addMultipleMailTypes(List<String> mailTypes) {
        $(linkMultipleMailType).click();
        for (String mailType : mailTypes) {
            selectMultiMailType(mailType);
            $(addMailTypeBtn).click();

        }

    }


    /**
     * Add Aprrover
     *
     * @param arroverName
     */
    public void addApprover(String arroverName,String approver) {
        $(approverSearchBox).clear();
        $(approverSearchBox).sendKeys(arroverName);
        $(searchBtn).click();
        if(approver!=null) {
            $(approverCheckBox).click();
        }
        $(createdByCheckBox).click();
        $(addApproverOkBtn).click();
    }

    /**
     * Function to select Mail Type
     *
     * @param mailType
     */
    public void selectSingleMailType(String mailType) {
        Select selectMailType = new Select(driver.findElement(By.xpath("//select[@id='selApprovableItems']")));
        selectMailType.selectByVisibleText(mailType);
    }


    /**
     *
     */
    public void selectMultiMailType(String mailTYpe) {
        Select selectMultiMail = new Select(driver.findElement(By.xpath("//select[@id='bidi_addApprovableItem_AVAIL']")));
        selectMultiMail.selectByVisibleText(mailTYpe);
    }

    /**
     * Function to Accept or Reject Mail from Approver
     *
     * @param accept
     */
    public void acceptOrReject(boolean accept) {
        if (accept) {
            $(acceptBtn).click();
        }
        $(rejectBtn).click();
    }


}
