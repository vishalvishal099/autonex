package com.oracle.babylon.steps.project;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.ProjectDataCreator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Project.ProjectPage;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public class ProjectSteps {

    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private Navigator navigator = new Navigator();
    private DataStore dataStore = new DataStore();
    private CommonMethods commonMethods = new CommonMethods();
    private ProjectPage projectPage = new ProjectPage();
    private ProjectDataCreator projectDataCreator = new ProjectDataCreator();
    private Project project = null;

    /**
     * Code that contains a series of steps to create a project
     * @param dataTable
     * @throws Exception
     */
    @When("we login and create project")
    public void weLoginAndCreateProject(DataTable dataTable) throws Exception {
        //Retrieve the data from userData.json file
        Map<String, Map<String,String>> mapOfMap =  dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user1");
        //Get project fields from the project data table

        Map<String, String> projectInfoMap = dataTable.asMaps().get(0);
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), null);

        //Set the project data
        projectDataCreator.generateProjectData(projectInfoMap);
        navigator.getMenuSubmenu("Setup", "Create Project");
        commonMethods.switchToFrame(WebDriverRunner.getWebDriver(), "frameMain");
        //Fill up the create project ui
        projectPage.fillUpProjectFields();
        //Update the project details in the userData.json
        projectPage.enterProjectDetailsToFile();
        project = dataStore.getProjectInfo("project");
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), project.getProjectName());
    }
}
