package com.oracle.babylon;


import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import com.oracle.babylon.Utils.helper.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.MalformedURLException;


public class Helper {

    DriverFactory driverFactory = new DriverFactory();

    /**
     * Function to launch the browser
     * @throws MalformedURLException
     */
    @Before
    public void driverSetup() throws MalformedURLException{
        WebDriver driver = driverFactory.getDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    /**
     * Function to handle the close of the test execution
     * @param scenario
     * @throws IOException
     */
    @After
    public void tearDown(Scenario scenario)  throws IOException {
        WebDriver driver = WebDriverRunner.getWebDriver();
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            CommonMethods.takeSnapshot(driver, screenshotName);
        }
        closeBrowser();
    }

    /**
     * Function to quit the browser
     */
    public void closeBrowser() {

            WebDriverRunner.getWebDriver().quit();


    }

}
