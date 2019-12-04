package com.oracle.babylon.pages.Document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.SchemaHelperPage;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DocumentTableConverter;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Document;
import io.cucumber.datatable.DataTable;
import org.apache.http.HttpResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

/**
 * Class file that contains methods related to the operations for Documents
 * Author : susgopal
 */
public class DocumentPage extends Navigator {

    //Initialization of web elements
    private By searchDocumentQuery = By.xpath("//input[@id='searchQuery']");
    private By resultTable = By.xpath("//table[@id='resultTable']");
    private By resultTableRow = By.xpath("//table[@id='resultTable']//tbody//tr");
    private By searchDocumentByNo = By.xpath("//input[@id='docno']");

    private By editPreferencesLabel = By.xpath("//h1[text()='Edit Preferences']");

    /**
     * Funtion for the Upload Document API, please refer the Aconex API documentation for more details
     *
     * @param userId            user for which basic credentials are to be created
     * @param documentTableName identifier for the document table
     * @return the document number that has been uploaded
     * @throws IOException
     * @throws InterruptedException
     */
    public String uploadDocumentAPI(String userId, String documentTableName, DataTable dataTable, String projectId) {
        //The data is taken from userData.json file and we search for the project in admin tool
        new DocumentTableConverter().createDocumentData(documentTableName, dataTable);
        String basicAuth = basicAuthCredentialsProvider(userId);

        Document document = dataStore.getDocument(documentTableName);

        document = setMandatoryFields(document, userId, projectId);
        //Creating the request body template
        StringBuilder documentRequestBody = new StringBuilder(CommonMethods.convertMaptoJsonString(document));

        //Updating the request body according to the required template
        documentRequestBody.insert(0, "{ \"document\":");
        documentRequestBody.append("}");
        String requestBodyXML = "--myboundary\n\n" + commonMethods.convertJsonStringToXMLString(documentRequestBody.toString()) + "\n--myboundary";
        requestBodyXML = capitalizeXMLTags(requestBodyXML, "<");
        requestBodyXML = capitalizeXMLTags(requestBodyXML, "</");

        //Forming the url
        String url = configFileReader.getApplicationUrl() + "api/projects/" + projectId + "/register";
        //executing the api request
        HttpResponse response = apiRequest.postRequest(url, basicAuth, "multipart/mixed", requestBodyXML);
        //return the document number
        return document.getDocumentNumber();
    }


    /**
     * Method to retrieve the document schema through a api call
     * @param userId
     * @param projectId
     * @return
     */
    public HttpResponse getDocumentSchema(String userId, String projectId) {
        String basicAuth = basicAuthCredentialsProvider(userId);
        String url = configFileReader.getApplicationUrl() + "api/projects/" + projectId + "/register/schema";
        HttpResponse response = apiRequest.getRequest(url, basicAuth);
        return response;
    }


    /**
     * The tags have to capitalized to meet the format for the API request body
     *
     * @param input      XML String to be capitalized
     * @param searchChar the patterns after which the character is capitalized
     * @return the converted capitalized string
     */
    public String capitalizeXMLTags(String input, String searchChar) {
        char[] convertedCharArr = input.toCharArray();
        int index = input.indexOf(searchChar);
        //Iterating through the string and converting a character to capital letter afer matching
        while (index >= 0) {
            int replacePos = index + searchChar.length();
            convertedCharArr[replacePos] = Character.toUpperCase(convertedCharArr[replacePos]);
            index = input.indexOf(searchChar, replacePos + 1);
        }
        return new String(convertedCharArr);
    }

    /**
     * Search the document using keys in the field search by any query
     *
     * @param driver
     * @param identifier key to be searched
     */
    public void searchDocumentByQuery(WebDriver driver, String identifier) {
        commonMethods.waitForElement(driver, searchDocumentQuery, 2);
        $(searchDocumentQuery).sendKeys(identifier);
        $(searchDocumentQuery).pressEnter();
    }

    /**
     * Search the document by using the document number
     *
     * @param documentNumber key to be searched
     */
    public void searchDocumentNo(String documentNumber) {
        commonMethods.waitForElementExplicitly(2000);
        driver = WebDriverRunner.getWebDriver();
        commonMethods.switchToFrame(driver, "frameMain");
        commonMethods.waitForElement(driver, searchDocumentByNo);
        $(searchDocumentByNo).sendKeys(documentNumber);
        $(searchDocumentByNo).pressEnter();
    }

    /**
     * Function to return the table size
     *
     * @return
     */
    public int getTableSize() {

        $(resultTable).scrollTo();
        return Integer.parseInt($(resultTable).findElement(By.xpath("//tbody//input[@name='totalDocsInPage']")).getAttribute("value"));
    }

    /**
     * Function to return the table contents
     *
     * @param driver
     * @return
     */
    public List<Map<String, String>> returnTableData(WebDriver driver) {
        return commonMethods.convertUITableToHashMap(driver, resultTable, resultTableRow);

    }

    /**
     * Generate the basic auth credentials for api requests
     * @param userId userId to retireve the password and generate the auth credentials
     * @return basic auth string
     */
    public String basicAuthCredentialsProvider(String userId) {
        jsonMapOfMap = dataSetup.loadJsonDataToMap(filePath);
        userMap = jsonMapOfMap.get(userId);
        //Generating the basic auth for the api
        return apiRequest.basicAuthGenerator(userMap.get("username"), userMap.get("password"));
    }

    /**
     * Method to set the mandatory fields for Document API by retieving it from Document Schema
     * @param document document fields object
     * @param userId generate auth credentials
     * @param projectId projectid to retieve the schema
     * @return
     */
    public Document setMandatoryFields(Document document, String userId, String projectId){
        //Basic Mandatory fields for Document are Document Status ID, Document Type ID, Attribute 1 and Discipline
        //API response for Document Schema
        commonMethods.waitForElementExplicitly(configFileReader.getImplicitlyWait()*1000);
        HttpResponse documentSchemaResponse = getDocumentSchema(userId, projectId);
        List<String> mandatoryList;
        //Return the response body from the HTTP Response
        String responseString = commonMethods.returnResponseBody(documentSchemaResponse);
        SchemaHelperPage schemaHelper = new SchemaHelperPage();
        //Check if the document fields are not set in Document object. If not set, then retrieve from Document Schema response body and set it.
        if(document.getDiscipline() == null){
            mandatoryList = schemaHelper.retrieveValuesFromSchema(responseString, "Discipline", "Value");
            document.setDiscipline(mandatoryList.get(0));
        }

        if(document.getAttribute1() == null){
            mandatoryList = schemaHelper.retrieveValuesFromSchema(responseString, "Attribute1", "Value");
            document.setAttribute1(mandatoryList.get(0));
        }

        if(document.getDocumentStatusId() == 0){
            mandatoryList = schemaHelper.retrieveValuesFromSchema(responseString, "DocumentStatusId", "Id");
            document.setDocumentStatusId(Integer.parseInt(mandatoryList.get(0)));
        }

        if(document.getDocumentTypeId() == 0){
            mandatoryList = schemaHelper.retrieveValuesFromSchema(responseString, "DocumentTypeId", "Id");
            document.setDocumentTypeId(Integer.parseInt(mandatoryList.get(0)));
        }
        return document;
    }


}
