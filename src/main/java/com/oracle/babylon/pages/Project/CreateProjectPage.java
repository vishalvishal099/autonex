package com.oracle.babylon.pages.Project;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Hashtable;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class to perform some operations on the Project page
 */
public class CreateProjectPage extends Navigator{

    //Initialization of web elements
    private By projectNameTxtBox = By.name("ProjectName");
    private By projectShortNameTxtBox = By.name("ProjectShortName");
    private By projectCodeTxtBox = By.name("ProjectCode");
    private By projectTypeTxtBox = By.name("ProjectType");
    private By primaryRegisterTypeDrpDwn = By.name("RegisterType");
    private By defaultAccessLevel = By.name("defaultAccessLevel");
    private By addressTxtBox = By.name("ProjectAddress1");
    private By cityTxtBox = By.name("ProjectSuburb");
    private By countyTxtBox = By.name("ProjectState");
    private By postCodeTxtBox = By.name("ProjectPostcode");
    private By countryDrpDwn = By.name("ProjectCountry");
    private By projectStartDateTxtBox = By.xpath("//div//input[@name='ProjectStartDate_da']");
    private By estimatedCompletionDateTxtBox = By.xpath("//div//input[@name='ProjectStopDate_da']");
    private By projectValueTxtBox = By.name("ProjectValue");
    private By projectDescriptionTxtArea = By.name("ProjectDescription");
    private By saveBtn = By.id("btnSave");

    public void navigateToPage(){
        getMenuSubmenu("Setup", "Create Project");
    }
    /**
     * Method to fill up the project fields in the ui
     */
    public void fillUpProjectFields(Project project) {
        commonMethods.switchToFrame(WebDriverRunner.getWebDriver(), "frameMain");
        $(projectNameTxtBox).sendKeys(project.getProjectName());
        $(projectShortNameTxtBox).sendKeys(project.getProjectShortName());
        $(projectCodeTxtBox).sendKeys(project.getProjectCode());
        $(projectTypeTxtBox).sendKeys(project.getProjectType());
        Select select = new Select($(primaryRegisterTypeDrpDwn));
        select.selectByValue(project.getPrimaryRegisterType().toUpperCase());
        select = new Select($(defaultAccessLevel));
        select.selectByValue(project.getDefaultAccessLevel().toUpperCase());
        $(addressTxtBox).sendKeys(project.getProjectAddress());
        $(cityTxtBox).sendKeys(project.getCity());
        $(countyTxtBox).sendKeys(project.getCounty());
        select = new Select($(countryDrpDwn));
        select.selectByValue(project.getCountry());
        $(projectStartDateTxtBox).sendKeys(project.getProjectStartDate());
        $(estimatedCompletionDateTxtBox).sendKeys(project.getEstimatedCompletionDate());
        $(projectValueTxtBox).clear();
        $(projectValueTxtBox).sendKeys(project.getProjectValue());
        $(projectDescriptionTxtArea).sendKeys(project.getProjectDescription());
        $(saveBtn).click();

    }

    /**
     * Store the details of a project in the json data file for future references
     */
    public void enterProjectDetailsToFile(String userId, String projectIdentifier, User user){
        Map<String, Map<String, String>> mapOfMap = new Hashtable<>();
        char projectNumber = projectIdentifier.charAt(projectIdentifier.length()-1);
        String projectId = "project_id" + projectNumber;
        String projectName = "project_name" + projectNumber;
        String[] keys = {projectId, projectName};
        Map<String, String> valueMap = new Hashtable<>();
        valueMap.put(keys[0], user.getProjectId());
        valueMap.put(keys[1], user.getProjectName());
        mapOfMap.put(userId, valueMap);
        dataSetup.convertMapOfMapAndWrite(userId, mapOfMap, userDataPath);

    }


}
