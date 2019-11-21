package com.oracle.babylon.Utils.helper;


import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Ticket;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import org.apache.http.HttpResponse;

import java.io.IOException;

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
     * @param ticketTableName name of the ticket table in the data store
     * @return the response with the details of the ticket
     */
    public HttpResponse getJiraTicket(String ticketTableName) throws IOException {
        //Parsing the data store and fetching the required table
        ticket = dataStore.getTicket(ticketTableName);
        String url = configFileReader.getJiraUrl() + "/jira/rest/api/2/issue/" + ticket.getTicketId();
        String basicAuth = "Basic " + configFileReader.returnSSOAuthString();
        String headers = basicAuth + ", Accept:application/json";
        HttpResponse response = apiRequest.getRequest(url, headers);

        //String responseString = CommonMethods.entityToString(response.getEntity());

        return response;
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
     * @param ticketTableName name of the ticket table in the data store
     * @return
     */
    public HttpResponse addComment(String ticketTableName, String comment) {

        ticket = dataStore.getTicket(ticketTableName);
        String url = configFileReader.getJiraUrl() + "/jira/rest/api/2/issue/" + ticket.getTicketId() + "/comment";
        String basicAuth = "Basic " + configFileReader.returnSSOAuthString();
        String jsonString = "{ \"body\": " + comment + " }";
        HttpResponse response = apiRequest.postRequest(url, basicAuth, jsonString);
        return response;


    }




}
