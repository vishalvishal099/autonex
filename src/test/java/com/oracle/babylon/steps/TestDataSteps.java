package com.oracle.babylon.steps;

import com.oracle.babylon.worldHelper.setup.dataStore.DataStore;
import cucumber.api.java.en.Given;
import io.cucumber.datatable.DataTable;

public class TestDataSteps {

    private DataStore dataStore = new DataStore();
    @Given("^Vertical Table \"([^\"]*)\"$")
    public void verticalTable(String name, DataTable dataTable) throws Throwable {
        dataStore.setTable(name,dataTable);
    }
}
