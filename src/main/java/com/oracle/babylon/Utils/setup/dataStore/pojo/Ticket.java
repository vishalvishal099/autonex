package com.oracle.babylon.Utils.setup.dataStore.pojo;

/**
 * Class contains fields related to a Ticket, along with setters and getters for the same
 * Author : susgopal
 */
public class Ticket {
    private String ticketId;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }




}
