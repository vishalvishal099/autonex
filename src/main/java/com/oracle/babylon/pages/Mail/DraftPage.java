package com.oracle.babylon.pages.Mail;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class DraftPage extends MailPage {
    private By table =By.xpath("//table[@id='resultTable']");
    public void navigateAndVerifyPage() {
        driver.navigate().refresh();
        commonMethods.waitForElementExplicitly(3000);
        getMenuSubmenu("Mail", "Drafts");
        verifyPageTitle("Search Mail");
    }


    public void selectDraftMail(String mailNumber){
        searchMailNumber(mailNumber);
        WebElement subject = driver.findElement(table).findElement(By.xpath("//td[4]/a"));
        subject.click();
    }

    public void verifyMailInDraft(String mailNumber){
        searchMailNumber(mailNumber);
        Assert.assertTrue($(By.xpath("//tbody[@id='rowPerMailTableBody']")).text().contains(mailNumber));
    }


}
