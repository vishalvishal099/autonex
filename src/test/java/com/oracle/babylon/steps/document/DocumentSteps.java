package com.oracle.babylon.steps.document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Document.DocumentPage;
import com.oracle.babylon.pages.Document.DocumentRegisterPage;
import com.oracle.babylon.pages.Document.TransmittalPage;
import com.oracle.babylon.pages.Setup.ProjectSettingsPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Class to convert the test cases for document into Java methods
 * Author : susgopal
 */
public class DocumentSteps {

    String documentNumber = null;

    /**
     * Code to call the Register Document API. Used in Data creation for the framework
     *
     * @param documentTableName
     * @throws IOException
     * @throws InterruptedException
     */
    @Given("upload document with data \"([^\"]*)\" and write it in userData.json")
    public void uploadDocumentWithData(String documentTableName) throws InterruptedException, ParseException, IOException {
        DocumentPage documentPage = new DocumentPage();
        this.documentNumber = documentPage.uploadDocumentAPI(documentTableName);
        DataSetup dataSetup = new DataSetup();
        String[] attributeList = new String[]{"document", "docno"};
        ConfigFileReader configFileReader = new ConfigFileReader();
        dataSetup.writeIntoJson(attributeList, documentNumber, configFileReader.returnUserDataJsonFilePath());

    }

    /**
     * Code to search the document in the Document Register in Aconex
     *
     */
    @When("search document for user")
    public void searchDocumentForUser() throws IOException, ParseException {

        //Object creation and reference assignment
        Navigator navigator = new Navigator();
        DataStore dataStore = new DataStore();
        CommonMethods commonMethods = new CommonMethods();
        ConfigFileReader configFileReader = new ConfigFileReader();
        DocumentPage documentPage = new DocumentPage();
        //Retrieve document data from data store
        DataSetup dataSetup = new DataSetup();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> projectMap = mapOfMap.get("project");
        Map<String, String> userMap = mapOfMap.get("user");
        Map<String, String> docMap = mapOfMap.get("document");

        //Login to the server using the credentials, switching to the required project
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));
        WebDriver driver = WebDriverRunner.getWebDriver();
        commonMethods.selectSubMenu(driver, "Documents", "Document Register", "frameMain");
        documentPage.searchDocumentNo(driver, docMap.get("docno"));
    }

    /**
     * code to verify if the document is present in the server
     */
    @Then("verify if document is present")
    public void verifyIfDocumentIsPresent() {
        DocumentPage documentPage = new DocumentPage();
        WebDriver driver = WebDriverRunner.getWebDriver();
        int tableSize = documentPage.getTableSize();
        List<Map<String, String>> tableData = documentPage.returnTableData(driver);
        //We are searching a single document is present
        Assert.assertEquals(tableSize, "1");
        Assert.assertEquals(tableData.get(0).get("DocumentNo"), documentNumber);

    }

    @When("Login and lock the documents fields")
    public void weLoginAndLockTheDocumentsFields() throws IOException, ParseException {

        ConfigFileReader configFileReader = new ConfigFileReader();
        //The data is taken from userData.json file and we search for the project in admin tool
        DataSetup dataSetup = new DataSetup();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");

        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");

        //Locking in the field labels
        Navigator navigator = new Navigator();
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));
        ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage();
        projectSettingsPage.lockFieldsInDocuments();
    }

    @Then("verify if lock fields is disabled")
    public void verifyIfLockFieldsIsDisabled() {
        ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage();
        if(projectSettingsPage.isLockFieldsBtnEnabled()){
            Assert.fail("The lock fields button should be disabled");
        }
    }

    @When("Login and add a document attribute \"([^\"]*)\"")
    public void addAttribute(String attributeNumber) throws IOException, ParseException {
        ConfigFileReader configFileReader = new ConfigFileReader();
        //The data is taken from userData.json file and we search for the project in admin tool
        DataSetup dataSetup = new DataSetup();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");
        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");

        //Locking in the field labels
        Navigator navigator = new Navigator();
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));
        ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage();
        //Creating attributes
        projectSettingsPage.clickLabelToEdit(attributeNumber);
        String attributeValue = projectSettingsPage.addAttribute();
        new DataStore().storeAttributeInfo(attributeNumber, attributeValue);
    }


    @When("Login and create a transmittal")
    public void loginAndCreateATransmittal() throws IOException, ParseException {
        ConfigFileReader configFileReader = new ConfigFileReader();
        //The data is taken from userData.json file and we search for the project in admin tool
        DataSetup dataSetup = new DataSetup();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");
        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");
        String projectName = projectMap.get("projectname");
        //Locking in the field labels
        Navigator navigator = new Navigator();
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectName);
        searchDocumentForUser();
        DocumentRegisterPage documentRegisterPage = new DocumentRegisterPage();
        documentRegisterPage.selectDocAndNavigateToTransmittal();
        TransmittalPage transmittalPage = new TransmittalPage();
        transmittalPage.createBasicTransmittal();


    }
}
