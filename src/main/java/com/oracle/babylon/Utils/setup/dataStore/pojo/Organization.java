package com.oracle.babylon.Utils.setup.dataStore.pojo;

/**
 * Class that contains the fields related to the Organization, along with the setters and getters for the fields
 * Author : susgopal
 */
public class Organization {

    private String  organizationName;
    private String address;
    private String city;
    private String county;
    private String country;
    private String postcode;
    private String tradingName;
    private String orgAbbreviation;
    private String contactFirstName;
    private String contactLastName;
    private String contactEmailAddress;
    private String contactPhone;


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getOrgAbbreviation() {
        return orgAbbreviation;
    }

    public void setOrgAbbreviation(String orgAbbreviation) {
        this.orgAbbreviation = orgAbbreviation;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }
}
