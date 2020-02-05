package com.oracle.babylon.steps.setup;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Organization;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Mail.InboxPage;
import com.oracle.babylon.pages.Setup.MyOrganization.CreateUserPage;
import com.oracle.babylon.pages.Setup.PreferenceDefaultTab;
import com.oracle.babylon.pages.Setup.PreferenceOrganizationTab;
import com.oracle.babylon.pages.Setup.PreferenceUserTab;
import com.oracle.babylon.pages.User.NewAccountDetails;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.ProjectDataCreator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import cucumber.api.java.en.Given;

import java.util.Hashtable;
import java.util.Map;

public class CreateUserSteps extends Navigator {

    private Navigator navigator = new Navigator();
    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private NewAccountDetails newAccountDetails = new NewAccountDetails();
    private PreferenceOrganizationTab preferenceOrganizationTab = new PreferenceOrganizationTab();
    String filepath = configFileReader.getUserDataJsonFilePath();
    private User user = new User();
    private CreateUserPage createUserPage = new CreateUserPage();
    static Map<String, String> userDetail;

    @Given("{string} creates a {string} in same org with {string}")
    public void muserCreatesAUserInSameOrgWith(String userId, String newUser, String project) {
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(filepath);
        Map<String, String> userMap = mapOfMap.get(userId);
        char projectIndex = project.charAt(project.length() - 1);
        //Get project fields from the project data table
        user.setFullName(userMap.get("fullname"));
        user.setUsername(userMap.get("username"));
        user.setPassword(userMap.get("password"));
        user.setProjectName(userMap.get("project_name" + projectIndex));
        String projectToBeAdded = userMap.get("project_name" + projectIndex);
        navigator.loginAsUser(user);

        navigator.on(preferenceOrganizationTab, page -> {
            page.navigateAndVerifyPage();
            page.checkNonDefaultSettingsForOrganization("Send email notification to new users", "false");

        });
        navigator.on(createUserPage, page -> {
            page.navigateAndVerifyPage();
            userDetail = page.newUserDefaultDetail();
//            page.createUserWithPassword(userDetail);
            page.createUserWithProjectAndPassword(userDetail, projectToBeAdded);
        });
        String fullName = userDetail.get("firstname") + " " + userDetail.get("lastname");
        Map<String, Map<String, String>> newUserMapOfMap = new Hashtable<>();
        String[] keys = {"username", "password", "full_name", "project_name" + projectIndex, "project_id" + projectIndex, "org_name"};
        Map<String, String> valueMap = new Hashtable<>();
        valueMap.put(keys[0], userDetail.get("loginName"));
        valueMap.put(keys[1], "1990_ABcd1234");
        valueMap.put(keys[2], fullName);
        valueMap.put(keys[3], userMap.get("project_name" + projectIndex));
        valueMap.put(keys[4], userMap.get("project_id" + projectIndex));
        valueMap.put(keys[5], userMap.get("org_name"));
        newUserMapOfMap.put(newUser, valueMap);
        dataSetup.convertMapOfMapAndWrite(newUser, newUserMapOfMap, userDataPath);
    }
}
