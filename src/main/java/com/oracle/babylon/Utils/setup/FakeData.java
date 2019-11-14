package com.oracle.babylon.Utils.setup;
import com.github.javafaker.Faker;

import java.util.Random;

/**
 * Class that creates a company and document data every time a object of the class is created
 */
public class FakeData {

    private Faker faker = new Faker();
    /**
     * Constructor call to set the company and the document data
     */
    public FakeData(){
        setCompanyName();
        setDocumentNumber();
    }

    //Initialization of the variables
    private String companyName;
    private String documentNumber;

    /**
     * Function to generate company data and set it
     */
    public void setDocumentNumber(){

        Random random = new Random(1000);
        this.documentNumber = faker.company().industry() + "-" + random.nextInt();
    }

    /**
     * Function to return the document number
     * @return
     */
    public String getDocumentNumber()
    {
        return documentNumber;
    }

    /**
     * Function to return the company name
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Generate company name and set it
     */
    public void setCompanyName() {
        Faker faker = new Faker();
        this.companyName = faker.company().name();

    }

}
