package com.oracle.babylon.steps.mail;

import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.pages.Mail.ComposeMail;
import com.oracle.babylon.pages.Mail.ViewMail;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ViewMailSteps {
    private ViewMail viewMail = new ViewMail();
    private Navigator navigator = new Navigator();
    private ComposeMail composeMail = new ComposeMail();

    @Then("verify if \"([^\"]*)\" is created")
    public void viewMailValidation(String mailType){
        navigator.on(viewMail, page ->{
            page.verifyPageTitle("View Mail");
            Assert.assertEquals("Mail types do not match", mailType,  page.retrieveMailType());
            Assert.assertNotNull("No mail number is returned", page.retrieveMailNumber());
        });
    }


    @Then("verify {string} in preview")
    public void verifyInPreview(String mailAttribute) {
        navigator.on(viewMail, page ->{
            page.verifyPreview(mailAttribute);
        });
    }

    @And("user verify no error message on preview")
    public void userVerifyNoErrorMessageOnPreviewForDocument() {
        navigator.on(composeMail, ComposeMail::previewMail);
        navigator.on(viewMail, ViewMail::verifyNoError);
    }

    @Then("user verify {string} button on preview page")
    public void userVerifyButtonOnPreviewPage(String button) {
        navigator.on(viewMail, page ->{
            page.verifyButton(button);
        });
    }
}
