package com.oracle.babylon.Utils.setup.dataStore;

import com.oracle.babylon.Utils.setup.dataStore.pojo.*;
import io.cucumber.datatable.DataTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that ensures we have a copy of the data from the data tables across test case files in a execution
 * Author : vsinghsi
 */
public class DataStore {

    //Object initialization and assigning references to it
    private static Map<String, Map<String, String>> hashDataTable = new HashMap<>();
    private static Map<String, User> userHashMap = new HashMap<>();
    private static Map<String, Ticket> ticketHashMap = new HashMap<>();
    private static Map<String, Document> documentHashMap = new HashMap<>();
    private static Map<String, Organization> organizationHashMap = new HashMap<>();
    private static Map<String, Project> projectHashMap = new HashMap<>();
    private static Map<String, String> attributeHashMap = new HashMap<>();
    /**
     * Function to add the user details in a hash map
     * @param username name of the table
     * @param user user information pojo
     */
    public void addUser(String username, User user) {
        if (userHashMap.containsKey(username)) {
            userHashMap.remove(username);
        }
        userHashMap.put(username, user);
    }

    /**
     * Function to return a hash map of the user details
     * @param username name of the table in data store
     * @return
     */
    public User getUser(String username) {
        return userHashMap.get(username);
    }

    /**
     * Function to create a hash map of the ticket details
     * @param name name of the table in data store
     * @ticket ticket details pojo
     * @return hash map containing ticket details
     * @param ticket
     */
    public void addTicket(String name, Ticket ticket) {
        if (ticketHashMap.containsKey(ticket)) {
            ticketHashMap.remove(name);
        }
        ticketHashMap.put(name, ticket);
    }

    /**
     * Function that returns the ticket details in hash map
     * @param name name of the table in the data store
     * @return
     */
    public Ticket getTicket(String name) {
        return ticketHashMap.get(name);
    }

    /**
     * Function to create a hash map of the document details
     * @param name name of the table
     * @param document document pojo
     */
    public void setDocumentInfo(String name, Document document) {
        if (documentHashMap.containsKey(document)) {
            documentHashMap.remove(name);
        }
        documentHashMap.put(name, document);
    }

    /**
     * Returns the Document details in a  hash map
     * @param name name of the table in the data store
     * @return
     */
    public Document getDocumentInfo(String name) {
        return documentHashMap.get(name);
    }

    /**
     * Function to create a hash map of document details
     * @param name name of the data table
     * @param document pojo of the fields of the document
     */
    public void uploadDocument(String name, Document document) {
        if (documentHashMap.containsKey(document)) {
            documentHashMap.remove(name);
        }
        documentHashMap.put(name, document);
    }

    /**
     * Function to return the Document details in a hash map
     * @param name of the table in the data store
     * @return
     */
    public Document getDocument(String name) {
        return documentHashMap.get(name);
    }

    /**
     * Function to create a hash map from a data table
     * @param name name of the data table
     * @param dataTable the contents of the data table
     */
    public void setTable(String name, DataTable dataTable) {
        Map<String, String> hashTable = dataTable.asMap(String.class, String.class);
        hashDataTable.put(name, hashTable);
        if (hashTable.containsKey(name)) {
            hashTable.remove(name);
        }
        getTable(name);
    }

    /**
     * Function to return the hash map of the table
     */
    public Map<String, String> getTable(String tableName) {
        return hashDataTable.get(tableName);
    }

    /**
     * Create a hash map for the Organization data
     * @param name key value in the data store
     * @param organization Pojo of the organization that contains the values
     */
    public void storeOrganizationInfo(String name, Organization organization){
        if(organizationHashMap.containsKey(name)){
            organizationHashMap.remove(name);
        }
        organizationHashMap.put(name, organization);
    }

    /**
     * Function to return the Organization details in a hash map
     * @param name name of the table in data store
     * @return
     */
    public Organization getOrganizationInfo(String name){
        return organizationHashMap.get(name);
    }

    /**
     * Function to create a Project hash map
     * @param name name of the data table
     * @param project pojo of the Project that contains values
     */
    public void storeProjectInfo(String name, Project project){
        if(projectHashMap.containsKey(name)){
            projectHashMap.remove(name);
        }
        projectHashMap.put(name, project);
    }

    /**
     * Function to return the project details in a hash map
     * @param name
     * @return
     */
    public Project getProjectInfo(String name){
        return projectHashMap.get(name);
    }

    /**
     * Function to store the attribute value in a data store
     * @param key
     * @param attributeValue
     */
    public void storeAttributeInfo(String key, String attributeValue){
        attributeHashMap.put(key,attributeValue);
    }

    /**
     *
     */
    public Map<String, String> getAttributeHashMap(){
        return attributeHashMap;
    }

}

