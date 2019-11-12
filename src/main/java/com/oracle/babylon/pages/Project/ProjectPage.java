package com.oracle.babylon.pages.Project;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class to perform some operations on the Project page
 */
public class ProjectPage {
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

    /**
     * Method to fill up the project fields in the ui
     */
    public void fillUpProjectFields() {
        DataStore dataStore = new DataStore();

        Project project = dataStore.getProjectInfo("project");
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
     *
     * @throws Exception
     */
    public void enterProjectDetailsToFile() throws Exception {
        //Fetch the project id from the admin page
        DataStore dataStore = new DataStore();
        Project project = dataStore.getProjectInfo("project");
        DataSetup dataSetup = new DataSetup();
        ConfigFileReader configFileReader = new ConfigFileReader();

        CommonMethods commonMethods = new CommonMethods();
        WebDriver driver = WebDriverRunner.getWebDriver();
        //Search for the project in the xoogle search page
        String projectId = commonMethods.searchProject(driver, project.getProjectName());

        //Matching the key in the json file and replacing the value with the value from the map
        String[] projectKeysList = {"project", "projectId"};
        dataSetup.writeIntoJson(projectKeysList, projectId, configFileReader.returnUserDataJsonFilePath());
        projectKeysList = new String[]{"project", "projectname"};
        dataSetup.writeIntoJson(projectKeysList, project.getProjectName(), configFileReader.returnUserDataJsonFilePath());
    }


}
