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

    private ConfigFileReader configFileReader = new ConfigFileReader();
    private DataSetup dataSetup = new DataSetup();
    private DocumentPage documentPage = new DocumentPage();
    private Navigator navigator = new Navigator();
    private CommonMethods commonMethods = new CommonMethods();
    private WebDriver driver = null;
    private ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage();
    private DocumentRegisterPage documentRegisterPage = new DocumentRegisterPage();
    private TransmittalPage transmittalPage = new TransmittalPage();

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
        this.documentNumber = documentPage.uploadDocumentAPI(documentTableName);
        String[] attributeList = new String[]{"document", "docno"};
        dataSetup.writeIntoJson(attributeList, documentNumber, configFileReader.returnUserDataJsonFilePath());
    }

    /**
     * Code to search the document in the Document Register in Aconex
     */
    @When("search document for user")
    public void searchDocumentForUser() throws IOException, ParseException {

        //Retrieve document data from data store
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> projectMap = mapOfMap.get("project");
        Map<String, String> userMap = mapOfMap.get("user");
        Map<String, String> docMap = mapOfMap.get("document");

        //Login to the server using the credentials, switching to the required project
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));
        driver = WebDriverRunner.getWebDriver();
        navigator.getMenuSubmenu( "Documents", "Document Register");
        documentPage.searchDocumentNo(driver, docMap.get("docno"));
    }

    /**
     * code to verify if the document is present in the server
     */
    @Then("verify if document is present")
    public void verifyIfDocumentIsPresent() {
        driver = WebDriverRunner.getWebDriver();
        int tableSize = documentPage.getTableSize();
        List<Map<String, String>> tableData = documentPage.returnTableData(driver);
        //We are searching a single document is present
        Assert.assertEquals(tableSize, "1");
        Assert.assertEquals(tableData.get(0).get("DocumentNo"), documentNumber);

    }

    @When("Login and lock the documents fields")
    public void weLoginAndLockTheDocumentsFields() throws IOException, ParseException {

        //The data is taken from userData.json file and we search for the project in admin tool
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");

        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");

        //Locking in the field labels
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));

        projectSettingsPage.lockFieldsInDocuments();
    }

    @Then("verify if lock fields is disabled")
    public void verifyIfLockFieldsIsDisabled() {
        if (projectSettingsPage.isLockFieldsBtnEnabled()) {
            Assert.fail("The lock fields button should be disabled");
        }
    }

    @When("Login and add a document attribute \"([^\"]*)\"")
    public void addAttribute(String attributeNumber) throws IOException, ParseException {
        //The data is taken from userData.json file and we search for the project in admin tool
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");
        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");

        //Locking in the field labels
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectMap.get("projectname"));
        //Creating attributes
        projectSettingsPage.clickLabelToEdit(attributeNumber);
        String attributeValue = projectSettingsPage.createNewDocumentAttribute();
        new DataStore().storeAttributeInfo(attributeNumber, attributeValue);
    }


    @When("Login and create a transmittal")
    public void loginAndCreateATransmittal() throws IOException, ParseException {
        //The data is taken from userData.json file and we search for the project in admin tool
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());
        Map<String, String> userMap = mapOfMap.get("user");
        //Project info
        Map<String, String> projectMap = mapOfMap.get("project");
        String projectName = projectMap.get("projectname");
        //Locking in the field labels
        navigator.loginToServer(userMap.get("username"), userMap.get("password"), projectName);
        searchDocumentForUser();
        documentRegisterPage.selectDocAndNavigateToTransmittal();
        transmittalPage.createBasicTransmittal();


    }
}
