package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.DataStore;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

public class ComposeMail {

    WebDriver driver = WebDriverRunner.getWebDriver();
    private User user = null;
    Navigator navigator = new Navigator();
    DataStore dataStore = new DataStore();


    public void selectMenuSubMenu() {
        navigator.getMenuSubmenu("Mail", "Blank Mail");
        navigator.switchToFrame("frameMain");
    }

    public void composeMail(String user1, String data) {
        Map<String, String> table = dataStore.getTable(data);
        for (String tableData : table.keySet()) {

            switch (tableData) {
                case "Mail Type":
                    selectMailType(table.get(tableData));
                    break;
                case "Subject":
                    fillInSubject(table.get(tableData));
                    break;
                case "Attribute 1":
                    selectAttribute("Attribute 1", table.get(tableData));
                    break;
                case "Mail Body":
                    setMailBody(table.get(tableData));
                    break;

            }
        }
    }

    public void selectMailType(String mailType) {
        $(By.id("Correspondence_correspondenceTypeID")).selectOption(mailType);
    }

    public void fillInSubject(String subject) {
        $(By.id("Correspondence_subject")).setValue(subject);
    }

    public void selectAttribute(String attribute, String value) {

        $(By.xpath("//tr[td[label[contains(text(),'" + attribute + "')]]]/td[@class='contentcell']/div")).click();
        navigator.waitForElement();
        Select select = new Select(driver.findElement(By.xpath("//div[@id='attributeBidi_PRIMARY_ATTRIBUTE']//div[@class='uiBidi-left']//select")));
        navigator.waitForElement();
        select.selectByVisibleText(value);
        navigator.waitForElement();
        $(By.xpath("//button[@id='attributeBidi_PRIMARY_ATTRIBUTE_add']")).click();
        navigator.waitForElement();
        selectAttributeClickOK();
    }

    public void setMailBody(String mailBody) {
        switchToMailBodyFrame();
        $(".cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders").setValue(mailBody);
    }

    public void selectAttributeClickOK() {
        $(By.xpath("//button[@id='attributePanel-commit' and @title='OK']")).click();
    }

    public void switchToMailBodyFrame() {
        switchTo().defaultContent();
        switchTo().frame("frameMain");
        switchTo().frame(0); // driver.findElements(By.tagName("iframe")).size();
    }

    public void fillTo(String userTo) {
        user = dataStore.getUser(userTo);
        userTo = user.getFullName();
        navigator.switchToFrame("frameMain");
        $(By.xpath("//input[@name='SPEED_ADDRESS_TO']")).setValue(userTo);
        $(By.xpath("//input[@name='SPEED_ADDRESS_TO']")).pressEnter();
        navigator.waitForElement();
        $(By.xpath("//input[@name='SPEED_ADDRESS_TO']")).click();
    }

    public String send() {
        $(By.xpath("//button[@id='btnSend']")).click();
        $(".loading_progress").should(disappear);
        String mailNumber = $(By.xpath("//div[@class='mailHeader-numbers']//div[2]//div[2]")).getText();
        return mailNumber;
    }


}
