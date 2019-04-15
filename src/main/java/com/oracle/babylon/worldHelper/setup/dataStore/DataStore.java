package com.oracle.babylon.worldHelper.setup.dataStore;

import com.oracle.babylon.worldHelper.setup.dataStore.pojo.User;
import io.cucumber.datatable.DataTable;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    private static Map<String, Map<String, String>> hashDataTable = new HashMap<>();
    private static Map<String, User> hashmap = new HashMap<>();

    public void addUser(String username, User user) {
        if (hashmap.containsKey(username)) {
            hashmap.remove(username);
        }
        hashmap.put(username, user);
    }

    public User getUser(String username) {
        return hashmap.get(username);
    }


    public void setTable(String name, DataTable dataTable) {
        Map<String, String> hashTable = dataTable.asMap(String.class, String.class);
        hashDataTable.put(name, hashTable);
        if (hashTable.containsKey(name)) {
            hashTable.remove(name);
        }
        getTable(name);
    }

    public Map<String, String> getTable(String tableName) {
        return hashDataTable.get(tableName);
    }

}

