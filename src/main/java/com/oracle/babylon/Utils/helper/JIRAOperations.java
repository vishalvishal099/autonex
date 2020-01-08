package com.oracle.babylon.Utils.helper;


import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Ticket;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;


/**
 * Class that contains all the method related to the operations that can be performed in JIRA
 * Author : susgopal
 */
public class JIRAOperations {
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private DataStore dataStore = new DataStore();
    private Ticket ticket = new Ticket();
    private APIRequest apiRequest = new APIRequest();

    /**
     * Get the details of the jira ticket
     *
     * @param ticketId id of the ticket
     * @return the response with the details of the ticket
     */
    public HttpResponse getJiraTicketDetails(String ticketId) {
        //Parsing the data store and fetching the required table
        String url = configFileReader.getJiraUrl() + "/jira/rest/api/2/issue/" + ticketId;
        String basicAuth = "Basic " + configFileReader.returnSSOAuthString();
        String headers = basicAuth ;
        HttpResponse response =  apiRequest.getRequest(url, headers);
        HttpEntity entity = response.getEntity();
        try {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);

        } catch (Exception e){

        }
        return response;
    }

    public static RequestSpecification httpRequest;
    public static Response response;
    public static JsonPath extractor;
    String basicAuth = "Basic " + configFileReader.returnSSOAuthString();
    String getIssueIdUrl = configFileReader.getJiraUrl() + "/jira/rest/api/2/issue/";

    public String getJiraId(String issueID) {
        httpRequest = RestAssured.given();
        String url = getIssueIdUrl + issueID;
        httpRequest.header("Authorization", basicAuth);
        response = httpRequest.request(Method.GET, url);
        extractor = response.jsonPath();
        String issueId = extractor.get("id");
        return issueId;
    }


    /**
     * Function only declared, will write the implementation in the future
     *
     * @param ticketTableName name of the ticket table in the data store
     * @return
     */
    public HttpResponse updateJiraTicket(String ticketTableName) {
        return null;
    }

    /**
     * Function to add a comment to a Jira id provided
     * We use the encoded string to protect the password
     * @param ticketId name of the ticket table in the data store
     * @return
     */
    public HttpResponse addComment(String ticketId, String comment) {


        String url = configFileReader.getJiraUrl() + "/jira/rest/api/2/issue/" + ticketId + "/comment";

        String basicAuth = "Basic " + configFileReader.returnSSOAuthString();
        String jsonString = "{ \"body\": " + comment + " }";
        HttpResponse response = apiRequest.postRequest(url, basicAuth, jsonString);
        return response;
    }

    public String returnIssueId(String jiraId){
        HttpResponse response = getJiraTicketDetails(jiraId);
        HttpEntity entity = response.getEntity();
        try {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);
        } catch (Exception e){

        }
        JSONObject jsonObject = new JSONObject(response.getEntity());
        return jsonObject.get("id").toString();
    }

    public static  void main(String[] args) {
        JIRAOperations jira = new JIRAOperations();
        System.out.println(jira.getJiraTicketDetails("ACONEXQA-568"));
      // jira.addComment("ACONEXQA-568", "TEST COMMENT");
    }




}
