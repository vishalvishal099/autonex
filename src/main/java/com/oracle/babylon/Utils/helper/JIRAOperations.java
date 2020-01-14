package com.oracle.babylon.Utils.helper;


import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Ticket;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;

import java.util.*;


/**
 * Class that contains all the method related to the operations that can be performed in JIRA
 * Author : susgopal
 */
public class JIRAOperations {
    private ConfigFileReader configFileReader = new ConfigFileReader();
    private DataStore dataStore = new DataStore();
    private Ticket ticket = new Ticket();
    private APIRequest apiRequest = new APIRequest();
    String basicAuth = "Basic " + configFileReader.getSSOAuthString();
    public static Response response;
    public static JsonPath extractor;

    /**
     * Get the details of the jira ticket
     *
     * @param ticketId id of the ticket
     * @return the response with the details of the ticket
     */
    public Response getJiraTicketDetails(String ticketId) {

        String url = configFileReader.getJiraIssueUrl() + ticketId;
        Header header = new Header("Authorization", basicAuth);
        List<Header> headersList = new ArrayList<>();
        headersList.add(header);
        return apiRequest.execRequest(Method.GET, url, headersList, null);
    }

    /**
     * Method to return all the information about the test executions for a issue id
     * @param issueId
     * @return
     */
    public  Response getTestExecResults(String issueId){
        String url = configFileReader.getJiraExecutionUrl() + "?issueId=" + issueId;
        Header header = new Header("Authorization", basicAuth);
        List<Header> headersList = new ArrayList<>();
        headersList.add(header);
        return apiRequest.execRequest(Method.GET, url, headersList, null);
    }

    /**
     * Method to return the test execution id of a JIRA issue
     * @param issueId
     * @return
     */
    public  int returnLatestExecutionId(String issueId){
        response = getTestExecResults(issueId);
        extractor = response.jsonPath();
        List list = extractor.get("executions");

        Map<String, Object> map = (Map)list.get(0);
        return Integer.parseInt(map.get("id").toString());
    }

    /**
     *  Method to return the test execution id of a JIRA issue for a specific test execution version
     * @param issueId
     * @param versionName  release version for which test will be executed
     * @return
     */
    public  int returnLatestExecutionId(String issueId, String versionName){
        response = getTestExecResults(issueId);
        extractor = response.jsonPath();
        List list = extractor.get("executions");
        Map<String, Object> table = new Hashtable<>();
        Iterator<HashMap<String,Object>> iterator = list.iterator();
        while (iterator.hasNext()){
            table = iterator.next();
            if(table.get("versionName").equals(versionName)){
                return Integer.parseInt(table.get("id").toString());
            }
        }
        return -1;
    }

    /**
     * Updates the result of the test execution
     * @param executionid
     * @param statusId
     * @return
     */
    public Response updateLatestExecutionStatus(int executionid, int statusId){
        Map<String, Object> updateBody = new Hashtable<String, Object>();
        updateBody.put("status", statusId);
        String requestBody = CommonMethods.convertMaptoJsonString(updateBody);
        String url = configFileReader.getJiraExecutionUrl() + "/" + executionid + "/execute";
        Header authHeader = new Header("Authorization", basicAuth);
        Header contentTypeHeader = new Header("Content-Type", "application/json");
        List<Header> headersList = new ArrayList<>();
        headersList.add(authHeader);
        headersList.add(contentTypeHeader);
        return apiRequest.execRequest(Method.PUT, url, headersList, requestBody);
    }



    public String getJiraId(String ticketId) {

        response = getJiraTicketDetails(ticketId);
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
     *
     * @param ticketId name of the ticket table in the data store
     * @return
     */
    public HttpResponse addComment(String ticketId, String comment) {


        String url = configFileReader.getJiraIssueUrl() + ticketId + "/comment";

        String basicAuth = "Basic " + configFileReader.getSSOAuthString();
        String jsonString = "{ \"body\": " + comment + " }";
        HttpResponse response = apiRequest.postRequest(url, basicAuth, jsonString);
        return response;
    }



    public static void main(String[] args) {
        JIRAOperations jira = new JIRAOperations();
        jira.getJiraTicketDetails("ACONEXQA-568");
        // jira.addComment("ACONEXQA-568", "TEST COMMENT");
    }


}
