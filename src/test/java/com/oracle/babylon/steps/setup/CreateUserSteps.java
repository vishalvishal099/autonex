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

import java.util.Map;

public class CreateUserSteps extends Navigator {

    private Navigator navigator = new Navigator();
    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private NewAccountDetails newAccountDetails = new NewAccountDetails();
    private PreferenceOrganizationTab preferenceOrganizationTab = new PreferenceOrganizationTab();
    String filepath = configFileReader.getUserDataJsonFilePath();
    private User user = new User();
    private CreateUserPage createUserPage = new  CreateUserPage();
    static Map<String, String> userDetail;

    @Given("{string} creates a {string} in same org with {string}")
    public void muserCreatesAUserInSameOrgWith(String userId, String newUser ,String project) {
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(filepath);
        Map<String, String> userMap = mapOfMap.get(userId);
        Map<String, String> projectMap = mapOfMap.get(project);

        //Get project fields from the project data table
        user.setFullName(userMap.get("fullname"));
        user.setUserName(userMap.get("username"));
        user.setPassword(userMap.get("password"));
        user.setProject(projectMap.get("projectname"));
        navigator.loginAsUser(user);

        navigator.on(preferenceOrganizationTab, page -> {
            page.navigateAndVerifyPage();
            page.checkNonDefaultSettingsForOrganization("Send email notification to new users","false");

        });
        navigator.on(createUserPage, page -> {
            page.navigateAndVerifyPage();
            userDetail = page.newUserDefaultDetail();
            page.createUserWithPassword(userDetail);
        });
        String fullName = userDetail.get("firstname")+" "+userDetail.get("lastname");
        String[] keys = {"username", "password", "fullname"};
        String[] userKeyList = {newUser, keys[0]};
        dataSetup.writeIntoJson(userKeyList, userDetail.get("loginName"), filePath);
        userKeyList = new String[]{newUser, keys[1]};
        dataSetup.writeIntoJson(userKeyList, "1990_ABcd1234", filePath);
        userKeyList = new String[]{newUser, keys[2]};
        dataSetup.writeIntoJson(userKeyList, fullName, filePath);


    }
}
