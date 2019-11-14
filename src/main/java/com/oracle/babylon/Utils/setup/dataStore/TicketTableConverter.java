package com.oracle.babylon.Utils.setup.dataStore;

import com.oracle.babylon.Utils.setup.dataStore.pojo.Ticket;
import io.cucumber.datatable.DataTable;

import java.util.Map;

/**
 * Class to convert the data table values and store it in the Ticket pojo
 * Author : susgopal
 */
public class TicketTableConverter {
    private Ticket ticket = new Ticket();

    /**
     * Function to convert the data table and store it in the Ticket pojo. Also create a data store table for ticket
     *
     * @param name      name of the data table
     * @param dataTable actual contents of the table
     */
    public void createTicketData(String name, DataTable dataTable) {

        Map<String, String> ticketHashMap = dataTable.transpose().asMap(String.class, String.class);
        ticket.setTicketId(ticketHashMap.get("ID"));
        ticket.setDescription(ticketHashMap.get("Description"));
        //Creating a data store table
        new DataStore().addTicket(name, ticket);
    }
}
