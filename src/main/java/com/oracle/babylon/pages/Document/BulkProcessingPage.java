package com.oracle.babylon.pages.Document;

import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;

public class BulkProcessingPage extends Navigator {

    //Initialization of Web Elements
    private By newBulkProcessing= By.xpath("//div[@class='uiButton-label' and text()='New Bulk Process']");
    private By uploadButton=By.xpath("//div[@class='uiButton-label' and text()='Upload']");

    private By superCearchTextBox=By.xpath("//input[@name='rawQuery']");
    private By searchButton=By.xpath("//div[@class='uiButton-label' and text()='Search']");
    private By clearButton=By.xpath("//div[@class='uiButton-label' and text()='Clear']");

    private By selectPageSize=By.xpath("//select[@id='pageSize']");
    private By selectSortBy=By.xpath("//select[@id='jobSortField']");
    private By checkboxMyJobsOnly=By.xpath("//input[@id='showingUserOnly']");

    private By title=By.xpath("//input[@name='importTitle']");
    private By selectStatus=By.xpath("//select[@id='importStatus_jobs']");
    private By selectType=By.xpath("//select[@id='importTypeId']");
    private By tabDrafts=By.xpath("//li[@id='1_list']");
    private By ShowOrHideSearchFields=By.xpath("//img[@id='toggleModeIcon']");

    //New Bulk processing Job
    private By matadataTemplate=By.xpath("//div[@class='uiButton-label' and text()='Metadata Template ']");
    private By copyButton=By.xpath("//div[@class='uiButton-label' and text()='Copy']");
    private By backButton=By.xpath("//button[@id='btnBack']");

    private By localOrComputer=By.xpath("//div[@class='uiButton-label' and text()='Local Computer/Network']");
    private By temporaryFiles=By.xpath("//div[@class='uiButton-label' and text()='Temporary Files']");
    private By AttachFileCancelButton=By.xpath("//button[@id='btnchooseManifestSource_cancel']");





}
