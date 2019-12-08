package com.oracle.babylon.steps.mail;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Mail.ViewMail;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ViewMailSteps {
    private ViewMail viewMail = new ViewMail();
    private Navigator navigator = new Navigator();

    @Then("verify if \"([^\"]*)\" is created")
    public void viewMailValidation(String mailType){
        navigator.on(viewMail, page ->{
            page.verifyPageTitle("View Mail");
            Assert.assertEquals("Mail types do not match", mailType,  page.retrieveMailType());
            Assert.assertNotNull("No mail number is returned", page.retrieveMailNumber());
        });
    }
}
