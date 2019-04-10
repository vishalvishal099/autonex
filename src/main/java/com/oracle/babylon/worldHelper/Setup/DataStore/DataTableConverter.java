package com.oracle.babylon.worldHelper.Setup.DataStore;


import com.oracle.babylon.worldHelper.Setup.utils.ConfigFileReader;
import io.cucumber.datatable.DataTable;

import java.util.Map;

public class DataTableConverter {
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
