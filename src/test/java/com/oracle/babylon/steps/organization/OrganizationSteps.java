package com.oracle.babylon.steps.organization;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.OrganizationUserCreator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminHome;
import com.oracle.babylon.pages.Admin.AdminSearch;
import com.oracle.babylon.pages.Admin.UserInformation;
import com.oracle.babylon.pages.Organization.RegisterOrganizationPage;
import com.oracle.babylon.pages.User.NewAccountDetails;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

/**
 * Class to convert the organization test cases to java code
 * Author : susgopal,kukumavi
 */
public class OrganizationSteps {

    private ConfigFileReader configFileReader = new ConfigFileReader();
    private Navigator navigator = new Navigator();
    private OrganizationUserCreator orgUserObj = new OrganizationUserCreator();
    private WebDriver driver = null;
    private RegisterOrganizationPage registerOrganizationPage = new RegisterOrganizationPage();
    private AdminHome adminHome = new AdminHome();
    private AdminSearch adminSearch = new AdminSearch();
    private  UserInformation userInformation = new UserInformation();
    private DataSetup dataSetup = new DataSetup();
    private NewAccountDetails newAccountDetails = new NewAccountDetails();
    private User user = null;
    String userFilePath = configFileReader.getUserDataJsonFilePath();

    /**
     * Code which contains a sequence of steps to create a organization
     */
    @When("user {string} tries to create org")
    public void user_tries_to_create_organization(String userId) {
        //Register a organization
        navigator.on(orgUserObj, page -> {
            page.generateOrgData();
            page.addUser();
            navigator.openAconexUrl();
            navigator.clickRegisterLink();
        });
        navigator.on(registerOrganizationPage, page ->{
            page.verifyPage();
            page.fillOrganizationDetails();
            user = page.enterOrgUserDetailsToFile(userId);
        });
        navigator.loginAsUser(adminHome, AdminHome::verifyPage);
        navigator.on(adminSearch, page -> {
            page.navigateAndVerifyPage();
            page.clickSearchResults(user.getUsername(), user.getFullName());
        });
        navigator.on(userInformation, page -> {
            page.verifyPage();
            page.enableUser();
        });
    }

    /**
     * Code to verify if the user created when organization is created is able to login to the application
     * @throws IOException
     * @throws ParseException
     */
    @Then("user {string} is able to login to application")
    public void userIsAbleToLoginToApplication(String userId) {
        navigator.loginAsUser(newAccountDetails, userId, userFilePath, NewAccountDetails::verifyPage);

    }

    /**
     * User fills up the data when registering the organization, after organization is created
     * @param dataTable
     * @throws IOException
     * @throws ParseException
     */
    @When("user {string} needs to fill in the account details fields with data")
    public void fillUserDetails(String userId, DataTable dataTable)  {
        Map<String, String> userInfoMap = dataTable.asMaps().get(0);
        userIsAbleToLoginToApplication(userId);
        navigator.on(newAccountDetails, page-> {
            page.verifyPage();
            page.fillUserDetails(userInfoMap,userId);
        });

    }

}
