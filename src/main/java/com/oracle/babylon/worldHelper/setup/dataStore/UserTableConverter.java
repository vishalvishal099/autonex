package com.oracle.babylon.worldHelper.setup.dataStore;


import com.oracle.babylon.worldHelper.setup.dataStore.pojo.User;
import com.oracle.babylon.worldHelper.setup.utils.ConfigFileReader;
import io.cucumber.datatable.DataTable;

import java.util.Map;

public class UserTableConverter {
    public void addUser(String name, DataTable dataTable) {

        User user = new User();
        Map<String, String> hashUser = dataTable.transpose().asMap(String.class, String.class);
        user.setFullName(hashUser.get("Fullname"));

        user.setUserName(hashUser.get("Username"));

        user.setProject(hashUser.get("Projects"));

        String password = hashUser.containsKey("password") ? hashUser.get("password") : new ConfigFileReader().getPassword();

        user.setPassword(password);

        new DataStore().addUser(name, user);


    }
}
