package com.oracle.babylon.pages.Setup.MyOrganization;

import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.helper.Navigator;
import jdk.nashorn.internal.objects.Global;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class CreateUserPage extends Navigator {
    private By givenName = By.cssSelector("#givenName-input");
    private By familyName = By.cssSelector("#familyName-input");
    private By email = By.xpath("//div[@id='email']//input[@class='uiTextField-input']");
    private By loginName = By.xpath("//div[@id='loginName']//input[@class='uiTextField-input']");
    private By selectLanguage = By.xpath("//select[@id='selLanguages']");
    private By division = By.xpath("//select[@id='selDivision']");
    private By password = By.xpath("sudo chmod +x ~/.ssh/known_hosts");
    private By confirmPassword = By.xpath("//div[@id='passwordConfirm']//input[@class='uiPasswordField-input']");
    private By save = By.xpath("//div[@class='uiButton-label']");


    public void navigateAndVerifyPage() {
        getMenuSubmenu("Setup", "Create User");
        commonMethods.waitForElementExplicitly(5000);
        switchTo().frame("frameMain");
//        verifyPageTitle("Invite a new user");
    }

    public Map<String, String> newUserDefaultDetail() {
        Faker faker = new Faker();
        Map<String, String> userDetail = new HashMap<>();
        String firstName = faker.name().firstName();
        userDetail.put("firstname", firstName);
        String lastName = faker.name().lastName();
        userDetail.put("lastname", lastName);
        String email = firstName.charAt(0) + "." + lastName + "@aconex.com";
        userDetail.put("email", email);
        String loginName = firstName.charAt(0) + lastName;
        userDetail.put("loginName", loginName);

        return userDetail;
    }


    public void createUser(Map<String, String> userDetail){
        for (String key : userDetail.keySet()){
            switch (key.toLowerCase()){
                case "firstname":
                    $(givenName).sendKeys(userDetail.get(key));
                    break;
                case "lastname":
                    $(familyName).sendKeys(userDetail.get(key));
                    break;
                case "email":
                    $(email).sendKeys(userDetail.get(key));
                    break;
                case "loginname":
                    $(loginName).sendKeys(userDetail.get(key));
                    break;
            }
        }
        $(save).click();
    }

    public void createUserWithPassword(Map<String, String> userDetail){
        $(password).sendKeys("1990_ABcd1234");
        $(confirmPassword).sendKeys("1990_ABcd1234");
        createUser(userDetail);
    }


}
