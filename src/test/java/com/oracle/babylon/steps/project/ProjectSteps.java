package com.oracle.babylon.steps.project;

import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.ProjectDataCreator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminHome;
import com.oracle.babylon.pages.Admin.AdminSearch;
import com.oracle.babylon.pages.Project.CreateProjectPage;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

import java.util.Map;

public class ProjectSteps {

    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private Navigator navigator = new Navigator();
    private DataStore dataStore = new DataStore();
    private CommonMethods commonMethods = new CommonMethods();
    private CreateProjectPage createProjectPage = new CreateProjectPage();
    private ProjectDataCreator projectDataCreator = new ProjectDataCreator();
    private Project project = null;
    String userDataPath = configFileReader.getUserDataJsonFilePath();
    AdminHome adminHome = new AdminHome();
    AdminSearch adminSearch = new AdminSearch();
    private User user = new User();
    /**
     * Code that contains a series of steps to create a project
     * @param dataTable
     * @throws Exception
     */
    @When("user \"([^\"]*)\" login and create \"([^\"]*)\"")
    public void weLoginAndCreateProject(String userId, String projectNumber, DataTable dataTable) throws Exception {
        //Retrieve the data from userData.json file
        Map<String, Map<String,String>> mapOfMap =  dataSetup.loadJsonDataToMap(userDataPath);
        Map<String, String> userMap = mapOfMap.get(userId);

        //Get project fields from the project data table
        user.setFullName(userMap.get("full_name"));
        user.setUsername(userMap.get("username"));
        user.setPassword(userMap.get("password"));
        Map<String, String> projectFeatureDataMap = dataTable.asMaps().get(0);

        navigator.loginAsUser(user);
        //Set the project data
        projectDataCreator.generateProjectData(projectFeatureDataMap);
        project = dataStore.getProjectInfo("project");
        navigator.on(createProjectPage, page -> {
            page.navigateToPage();
            //Fill up the create project ui
            page.fillUpProjectFields(project);
            //Update the project details in the userData.json

        });

        navigator.loginAsUser(adminHome, AdminHome::verifyPage);
        navigator.on(adminSearch, page -> {
            page.navigateAndVerifyPage();
            user.setProjectId(page.returnResultId(project.getProjectName()));
            user.setProjectName(project.getProjectName());

        });

        navigator.on(createProjectPage, page -> {
            page.enterProjectDetailsToFile(userId, projectNumber, user);
        });

    }
}
