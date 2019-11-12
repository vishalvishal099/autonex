package com.oracle.babylon.steps.organization;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.OrganizationUserCreator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminHome;
import com.oracle.babylon.pages.Admin.AdminSearch;
import com.oracle.babylon.pages.Admin.UserInformation;
import com.oracle.babylon.pages.Organization.OrganizationPage;
import com.oracle.babylon.pages.User.UserDetails;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

/**
 * Class to convert the organization test cases to java code
 * Author : susgopal
 */
public class OrganizationSteps {

    /**
     * Code which contains a sequence of steps to create a organization
     * @throws IOException
     * @throws ParseException
     * @throws InterruptedException
     */
    @When("^user tries to create a organization$")
    public void createOrganization() throws IOException, ParseException, InterruptedException {
        //Register a organization
        Navigator navigator = new Navigator();
        OrganizationUserCreator orgUserObj = new OrganizationUserCreator();
        orgUserObj.generateOrgData();
        orgUserObj.addUser();
        navigator.openAconexUrl();
        navigator.clickRegisterLink();

        //Enter details of organization in a file
        OrganizationPage organizationPage = new OrganizationPage();
        organizationPage.fillOrganizationDetails();
        User user = organizationPage.enterOrgUserDetailsToFile();

        //Enable the user for the organization
        WebDriver driver = WebDriverRunner.getWebDriver();
        navigator.openAconexUrl();
        navigator.loginAsAdmin();
        AdminHome adminHome = new AdminHome(driver);
        adminHome.clickSetupBtn();
        adminHome.clickSearchBtn();
        AdminSearch adminSearch = new AdminSearch(driver);
        adminSearch.clickSearchResults(user.getUserName(), user.getFullName());
        UserInformation userInformation = new UserInformation();
        userInformation.enableUser();
    }

    /**
     * Code to verify if the user created when organization is created is able to login to the application
     * @throws IOException
     * @throws ParseException
     */
    @Then("user is able to login to application")
    public void userIsAbleToLoginToApplication() throws IOException, ParseException {
        CommonMethods commonMethods = new CommonMethods();
        DataSetup dataSetup = new DataSetup();
        ConfigFileReader configFileReader = new ConfigFileReader();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");
        Navigator navigator = new Navigator();
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), null);

    }

    /**
     * User fills up the data when registering the organization, after organization is created
     * @param dataTable
     * @throws IOException
     * @throws ParseException
     */
    @When("user needs to fill in the account details fields with data")
    public void userNeedsToFillInTheAccountDetailsFieldsWithData(DataTable dataTable) throws IOException, ParseException {

        Map<String, String> userInfoMap = dataTable.asMaps().get(0);
        userIsAbleToLoginToApplication();
        UserDetails userDetails = new UserDetails();
        userDetails.fillUserDetails(userInfoMap);

    }
}
