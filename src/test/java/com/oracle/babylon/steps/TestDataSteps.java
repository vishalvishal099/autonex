package com.oracle.babylon.steps;

import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.DocumentTableConverter;
import com.oracle.babylon.Utils.setup.dataStore.TicketTableConverter;
import com.oracle.babylon.Utils.setup.dataStore.UserTableConverter;
import cucumber.api.java.en.Given;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TestDataSteps {

    private DataStore dataStore = new DataStore();

    @Given("^User Data \"([^\"]*)\"$")
    public void userData(String name, DataTable dataTable) throws Throwable {
        new UserTableConverter().addUser(name, dataTable);
    }

    @Given("^Vertical Table \"([^\"]*)\"$")
    public void verticalTable(String name, DataTable dataTable) throws Throwable {
        dataStore.setTable(name,dataTable);
    }

    @Given("Jira Data \"([^\"]*)\"$")
    public void jiraData(String name, DataTable dataTable) throws Throwable  {
        new TicketTableConverter().createTicketData(name, dataTable);
    }

}
