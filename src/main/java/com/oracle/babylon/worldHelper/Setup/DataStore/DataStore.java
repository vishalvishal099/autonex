package com.oracle.babylon.worldHelper.Setup.DataStore;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    public  static Map<String, User> hashmap = new HashMap<>();

    public void addUser(String username, User user) {
       if( hashmap.containsKey(username)){
           hashmap.remove(username);
       }
        hashmap.put(username, user);
    }

    public User getUser(String username){
        return hashmap.get(username);
    }

}

