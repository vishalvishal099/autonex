package com.oracle.babylon.steps.document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminSearch;
import com.oracle.babylon.pages.Document.DocumentRegisterPage;
import com.oracle.babylon.pages.Document.TransmittalPage;
import com.oracle.babylon.pages.Setup.ProjectSettingsPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to convert the test cases for document into Java methods
 * Author : susgopal
 */
public class DocumentSteps {

    private ConfigFileReader configFileReader = new ConfigFileReader();
    private DataSetup dataSetup = new DataSetup();
    private DocumentRegisterPage documentRegisterPage = new DocumentRegisterPage();
    private Navigator navigator = new Navigator();
    private CommonMethods commonMethods = new CommonMethods();
    private WebDriver driver = null;
    private ProjectSettingsPage projectSettingsPage = new ProjectSettingsPage();
    private TransmittalPage transmittalPage = new TransmittalPage();
    AdminSearch adminSearch = new AdminSearch();

    String documentNumber = null;
    String filePath = configFileReader.returnUserDataJsonFilePath();



    /**
     * Code to call the Register Document API. Used in Data creation for the framework
     *
     * @param documentTableName
     * @throws IOException
     * @throws InterruptedException
     */
    @Given("upload document for user {string} with data {string} and write it in userData.json")
    public void uploadDocumentWithData(String userId, String documentTableName, DataTable dataTable){
        Map<String, String> projectMap = dataSetup.loadJsonDataToMap(filePath).get("project1");
        String projectId = projectMap.get("projectId");
        this.documentNumber = documentRegisterPage.uploadDocumentAPI(userId, documentTableName, dataTable, projectId);
        String[] attributeList = new String[]{"document1", "docno"};
        dataSetup.writeIntoJson(attributeList, documentNumber, filePath);
    }

    /**
     * Code to search the document in the Document Register in Aconex
     */
    @When("search document for user \"([^\"]*)\"")
    public void searchDocumentForUser(String userId) {

        //Retrieve document data from data store
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(filePath);
        Map<String, String> docMap = mapOfMap.get("document1");

        navigator.loginAsUser(documentRegisterPage, userId, filePath, page -> {
            page.navigateToDocumentRegisterAndVerify();
            page.searchDocumentNo( docMap.get("docno"));
        });
    }

    /**
     * code to verify if the document is present in the server
     */
    @Then("verify if document is present")
    public void verifyIfDocumentIsPresent() {
        driver = WebDriverRunner.getWebDriver();
        int tableSize = documentRegisterPage.getTableSize();
        List<Map<String, String>> tableData = documentRegisterPage.returnTableData(driver);
        //We are searching a single document is present
        Assert.assertEquals(tableSize, 1);
        this.documentNumber = dataSetup.loadJsonDataToMap(filePath).get("document1").get("docno");
        Assert.assertEquals(tableData.get(0).get("Document No"), documentNumber);

    }

    @When("Login and lock the documents fields for user \"([^\"]*)\"")
    public void weLoginAndLockTheDocumentsFields(String userId) {
        navigator.loginAsUser(projectSettingsPage, userId, filePath, page -> {
            page.lockFieldsInDocuments();

        });
    }

    @Then("verify if lock fields is disabled")
    public void verifyIfLockFieldsIsDisabled() {

        if (projectSettingsPage.isLockFieldsBtnEnabled()) {
            Assert.fail("The lock fields button should be disabled");
        }
    }

    @When("Login for user \"([^\"]*)\" and add a document attribute \"([^\"]*)\"")
    public void addAttribute(String userId, final String attributeNumber)  {
        navigator.loginAsUser(projectSettingsPage, userId, filePath, page -> {
            page.navigateAndVerifyPage();
            page.clickLabelToEdit(attributeNumber);
            String attributeValue = page.createNewDocumentAttribute();
            new DataStore().storeAttributeInfo(attributeNumber, attributeValue);
        });
    }


    @When("Login for user \"([^\"]*)\" and create a mail of type transmittal, send to user")
    public void loginAndCreateATransmittal(String userId, DataTable dataTable) {

        searchDocumentForUser(userId);
        navigator.on(documentRegisterPage, page ->{
            page.selectDocAndNavigateToTransmittal();
        });
        transmittalPage.createBasicTransmittal(dataTable);


    }
}
