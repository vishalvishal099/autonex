package com.oracle.babylon.steps.document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminSearch;
import com.oracle.babylon.pages.Document.DocumentRegisterPage;
import com.oracle.babylon.pages.Document.MultipleFileUpload;
import com.oracle.babylon.pages.Document.TransmittalPage;
import com.oracle.babylon.pages.Setup.ProjectSettingsPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Hashtable;
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
    MultipleFileUpload multipleFileUpload = new MultipleFileUpload();

    String documentId = null;
    String userDataPath = configFileReader.getUserDataJsonFilePath();
    String docDataPath = configFileReader.getDocumentDataJsonFilePath();



    /**
     * Code to call the Register Document API. Used in Data creation for the framework
     *
     * @param documentTableName
     * @throws IOException
     * @throws InterruptedException
     */
    @Given("upload document for user {string} for project {string} with data {string} and write to {string}")
    public void uploadDocumentWithData(String userId, String projectIdentifier, String documentTableName,String documentNumber, DataTable dataTable){
        Map<String, String> userMap = dataSetup.loadJsonDataToMap(userDataPath).get(userId);
        String number = projectIdentifier.substring(projectIdentifier.length()-1);
        String projectId = "project_id" + number;
        projectId = userMap.get(projectId);
        this.documentId = documentRegisterPage.uploadDocumentAPI(userId, documentTableName, dataTable, projectId);
        Map<String, Map<String, String>> mapOfMap = new Hashtable<>();
        Map<String, String> mapToReplace = new Hashtable<>();
        String key = "doc_num" + number;
        mapToReplace.put(key, documentId);
        mapOfMap.put(documentNumber, mapToReplace);
        dataSetup.convertMapOfMapAndWrite(documentNumber, mapOfMap, configFileReader.getDocumentDataJsonFilePath());
    }

    /**
     * Code to search the document in the Document Register in Aconex
     */
    @When("search document {string} for user {string} and project {string}")
    public void searchDocumentForUser(String documentNumber, String userNumber, String projectNumber) {

        //Retrieve document data from data store
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(docDataPath);
        Map<String, String> docMap = mapOfMap.get(documentNumber);
        String number = documentNumber.substring(documentNumber.length()-1);
        navigator.loginAsUser(documentRegisterPage, userNumber, projectNumber, page -> {
            page.navigateToDocumentRegisterAndVerify();
            page.searchDocumentNo( docMap.get("doc_num"+ number));
        });
    }

    /**
     * code to verify if the document is present in the server
     */
    @Then("verify if document {string} is present")
    public void verifyIfDocumentIsPresent(String documentNumber) {
        driver = WebDriverRunner.getWebDriver();
        commonMethods.waitForElementExplicitly(2000);
        int tableSize = documentRegisterPage.getTableSize();
        List<Map<String, String>> tableData = documentRegisterPage.returnTableData(driver);
        //We are searching a single document is present
        Assert.assertEquals(tableSize, 1);
        String number = documentNumber.substring(documentNumber.length()-1);
        this.documentId = dataSetup.loadJsonDataToMap(docDataPath).get(documentNumber).get("doc_num" + number);
        Assert.assertEquals(tableData.get(0).get("Document No"), documentId);

    }

    @When("Login and lock the documents fields for user {string} and project {string}")
    public void weLoginAndLockTheDocumentsFields(String userId, String projectId) {

        navigator.loginAsUser(projectSettingsPage, userId, projectId, page -> {
            page.lockFieldsInDocuments();

        });
    }

    @Then("verify if lock fields is disabled")
    public void verifyIfLockFieldsIsDisabled() {
        commonMethods.waitForElementExplicitly(3000);
        if (projectSettingsPage.isLockFieldsBtnEnabled()) {
            Assert.fail("The lock fields button should be disabled");
        }
    }

    @When("Login for user {string} and project {string}, add a document attribute {string}")
    public void addAttribute(String userId, String projectId, final String attributeNumber)  {
        navigator.loginAsUser(projectSettingsPage, userId, projectId, page -> {
            page.navigateAndVerifyPage();
            page.navigateToDocFields();
            page.clickLabelToEdit(attributeNumber);
            String attributeValue = page.createNewDocumentAttribute();
            new DataStore().storeAttributeInfo(attributeNumber.toLowerCase(), attributeValue);
        });
    }


    @When("Login for user \"([^\"]*)\" and create a mail of type transmittal, send to user")
    public void loginAndCreateATransmittal(String userId, DataTable dataTable) {

       // searchDocumentForUser(userId);
        navigator.on(documentRegisterPage, page ->{
            page.selectDocAndNavigateToTransmittal();
        });
        transmittalPage.createBasicTransmittal(dataTable);


    }

    @Given("upload Multiple files")
    public void uploadMultipleFiles() {
       // multipleFileUpload.returnRequiredDate("yesterday");
       // multipleFileUpload.returnFileNames("C:\\Users\\susgopal\\AutomationCode\\cyrusAconex\\cyrusaconex\\src\\main\\resources");

        navigator.loginAsUser(multipleFileUpload, "user1" , userDataPath, page -> {

           navigator.getMenuSubmenu("Documents", "Multiple File Upload");
           page.clickMultiFileUploadBtn("C:\\Users\\susgopal\\AutomationCode\\cyrusAconex\\cyrusaconex\\src\\main\\resources");



        });
    }
}
