package com.oracle.babylon.pages.Document;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.APIRequest;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataSetup;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Document;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.pages.Admin.AdminTools;
import com.oracle.babylon.pages.Setup.EditPreferencesPage;
import com.oracle.babylon.pages.Setup.ProjectSettingsPage;
import org.apache.http.HttpResponse;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

import java.io.IOException;
import java.util.*;

/**
 * Class file that contains functions related to the operations for Documents
 * Author : susgopal
 */
public class DocumentPage {

    //Initialization of objects and assignment of references
    DataStore dataStore = new DataStore();
    ConfigFileReader configFileReader = new ConfigFileReader();

    //Initialization of web elemets
    private By searchDocumentQuery = By.id("searchQuery");
    private By resultTable = By.id("resultTable");
    private By resultTableRow = By.xpath("//table[@id='resultTable']//tbody//tr");
    private By searchDocumentByNo = By.id("docno");

    private By editPreferencesLabel = By.xpath("//h1[text()='Edit Preferences']");

    /**
     * Funtion for the Upload Document API, please refer the Aconex API documentation for more details
     *
     * @param documentTableName identifier for the document table
     * @return the document number that has been uploaded
     * @throws IOException
     * @throws InterruptedException
     */
    public String uploadDocumentAPI(String documentTableName) throws IOException, InterruptedException, ParseException {
        //Retrieving information for creating the request body and the basic auth credentials
        ConfigFileReader configFileReader = new ConfigFileReader();
        APIRequest apiRequest = new APIRequest();
        CommonMethods commonMethods = new CommonMethods();

        //The data is taken from userData.json file and we search for the project in admin tool
        DataSetup dataSetup = new DataSetup();
        Map<String, Map<String, String>> mapOfMap = dataSetup.loadJsonDataToMap(configFileReader.returnUserDataJsonFilePath());

        //Project Info
        Map<String, String> projectMap = mapOfMap.get("project");
        String projectName = projectMap.get("projectname");
        String projectId = searchProjectWrapper(projectName);

        //Generating the basic auth for the api
        Map<String, String> userMap = mapOfMap.get("user");
        String basicAuth = apiRequest.basicAuthGenerator(userMap.get("username"), userMap.get("password"));

        //Creating the request body template
        Map<String, String> attributeMap = mapOfMap.get("attribute");
        Document document = dataStore.getDocument(documentTableName);
        document.setAttribute1(attributeMap.get("attribute1"));
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
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.waitForElement(driver, searchDocumentQuery, 2);
        $(searchDocumentQuery).sendKeys(identifier);
        $(searchDocumentQuery).pressEnter();
    }

    /**
     * Search the document by using the document number
     *
     * @param driver
     * @param documentNumber key to be searched
     */
    public void searchDocumentNo(WebDriver driver, String documentNumber) {
        CommonMethods commonMethods = new CommonMethods();
        commonMethods.waitForElement(driver, searchDocumentByNo, 2);
        $(searchDocumentByNo).sendKeys(documentNumber);
        $(searchDocumentByNo).pressEnter();
    }

    /**
     * Function to return the table size
     *
     * @return
     */
    public int getTableSize() {
        return Integer.parseInt($(resultTable).findElement(By.xpath("//tbody//input[@name='totalDocsInPage']")).getAttribute("value"));
    }

    /**
     * Function to return the table contents
     *
     * @param driver
     * @return
     */
    public List<Map<String, String>> returnTableData(WebDriver driver) {
        CommonMethods commonMethods = new CommonMethods();
        return commonMethods.convertUITableToHashMap(driver, resultTable, resultTableRow);

    }

    /**
     * Function to parse the json file to retrieve fields to search for the project id from admin tool
     * @param projectName name of the project
     * @return project id of the project
     * @throws InterruptedException
     */
    public String searchProjectWrapper(String projectName) throws InterruptedException {

        //Retrieve the project id for a particular project
        WebDriver driver = WebDriverRunner.getWebDriver();
        CommonMethods commonMethods = new CommonMethods();
        String projectId =  commonMethods.searchProject(driver, projectName);
        driver.switchTo().defaultContent();
        return projectId;
    }




}
