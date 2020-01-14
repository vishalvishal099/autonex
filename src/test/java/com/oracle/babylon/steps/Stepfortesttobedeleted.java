package com.oracle.babylon.steps;

import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Setup.ConfigureUserRoleOrganizationtab;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Setup.*;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Setup.ProjectSetting.DocumentFields;
import com.oracle.babylon.pages.Setup.ProjectSetting.MailTypeSetting;
import com.oracle.babylon.pages.Setup.ProjectSetting.ProjectSettingProjectTab;
import com.oracle.babylon.pages.Setup.ProjectSetting.MailDocumentsRoleSetting;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Stepfortesttobedeleted {
    private Navigator navigator = new Navigator();
    private ComposeMail composeMail = new ComposeMail();
    private DataSetup dataSetup = new DataSetup();
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private EditPreferencesPage editPreferencesPage = new EditPreferencesPage();
    private AssignUserRoleOrganizationTab roleUserOrganization = new AssignUserRoleOrganizationTab();
    private AssignUserRoleProjectTab roleuserProject = new AssignUserRoleProjectTab();
    private ConfigureUserRoleProjectTab config = new ConfigureUserRoleProjectTab();
    private ConfigureUserRoleOrganizationtab Assign = new ConfigureUserRoleOrganizationtab();
    private CreateRolePage createRole = new CreateRolePage();
    private PreferenceUserTab prefuser = new PreferenceUserTab();
    private ProjectSettingProjectTab projectsetting = new ProjectSettingProjectTab();
    private MailDocumentsRoleSetting mailDocumentsRoleSetting = new MailDocumentsRoleSetting();
    private MailTypeSetting mailType = new MailTypeSetting();
    private DocumentFields docfield = new DocumentFields();


//    @When("^\"([^\"]*)\"  logs in to \"([^\"]*)\"$")
//    public void logs_in_to(String orgAdmin, String project) throws Exception {
//        //Retrieve the data from userData.json file
//        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
//        Map<String, String> userMap = mapOfMap.get(orgAdmin);
//        Map<String, String> projectMap = mapOfMap.get(project);
//        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));
////        roleUserPage.visitAssignUserRolePage();
//        navigator.getMenuSubmenu("Setup", "Assign User Roles");
//        roleUserPage.checkboxUserRole("3Huqe J80Hg","AcnxAdmin");
//    }

    @When("user \"([^\"]*)\" logs in to mail doc role setting")
    public void logsInToProjectUser(String user) throws IOException, ParseException {
        navigator.loginAsUser(mailDocumentsRoleSetting, user, page -> {
            page.navigateAndVerifyPage();
        });
    }


    @Then("user navigates to project tab {string} and {string} with {string}")
    public void userNavigatesToProjectTab(String type, String name, String paramater) {
        navigator.on(mailDocumentsRoleSetting, page -> {
            page.createRole(type, name, paramater);

        });
    }

    @And("user verify rolename {string}")
    public void userVerifyRolename(String name) {
        navigator.on(mailDocumentsRoleSetting, page -> {
            page.verifyNewRoleName(name);

        });
    }

    @And("user copy role from project")
    public void userCopyRoleFromProject() {
        navigator.on(mailType, page -> {
            page.editMailForms("Request for Inspection");

        });
    }

    @When("user \"([^\"]*)\" logs in to docfield tab")
    public void logsInToProject(String user) throws IOException, ParseException {
        navigator.loginAsUser(docfield, user, page -> {
            page.navigateAndVerifyPage();
        });
    }

    @Then("user select use field for {string} as {string}")
    public void userSelectUseFieldForAs(String arg0, String arg1) {
        navigator.on(docfield, page -> {
            page.useField(arg0, arg1);
        });
    }

    @Then("user select mandatory for {string} as {string}")
    public void userSelectMandatoryForAs(String arg0, String arg1) {
        navigator.on(docfield, page -> {
            page.setMandatory(arg0, arg1);
        });
    }

    @Then("user select editable inline for {string} as {string}")
    public void userSelectEditableInlineForAs(String arg0, String arg1) {
        navigator.on(docfield, page -> {
            page.setEditableInline(arg0, arg1);
        });
    }

    @Then("user edit list value for {string}")
    public void userEditListValueFor(String arg0) {
        navigator.on(docfield, page -> {
            page.editListValues(arg0);
        });
    }

    @Then("user deletes role {string}")
    public void userDeletesRoleRole_name(String role) {
        navigator.on(mailDocumentsRoleSetting, page -> {
            page.deleteRole(role);

        });
    }

    @Then("user deselect all org from role role {string}")
    public void userDeselctAllOrgFromRoleRole(String arg0) {
        navigator.on(mailDocumentsRoleSetting, page -> {
            page.deselectAllOrganisation(arg0);

        });
    }
}
