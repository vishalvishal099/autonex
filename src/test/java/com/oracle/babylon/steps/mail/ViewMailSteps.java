package com.oracle.babylon.steps.mail;

import com.oracle.babylon.pages.Mail.ViewMail;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ViewMailSteps {
    private ViewMail viewMail = new ViewMail();

    @Then("verify if \"([^\"]*)\" is created")
    public void viewMailValidation(String mailType){
        Assert.assertEquals("Mail types do not match", mailType,  viewMail.retrieveMailType());
        Assert.assertNotNull("No mail number is returned", viewMail.retrieveMailNumber());
    }
}
