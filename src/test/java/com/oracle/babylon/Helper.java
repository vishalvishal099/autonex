package com.oracle.babylon;


import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.MalformedURLException;


public class Helper {




    /**
     * Function to launch the browser
     * @throws MalformedURLException
     */
    @Before
    public void driverSetup() throws MalformedURLException{
      //  ZephyrStatusSingleton.getInstance("ACONEXQA-568");

        DriverFactory driverFactory = new DriverFactory();
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
        System.out.println("Test status--->" + scenario.getStatus());
        WebDriver driver = WebDriverRunner.getWebDriver();
        if (scenario.isFailed()) {
            scenario.embed(CommonMethods.takeSnapshot(driver),"image/png");
        }
        closeBrowser(driver);
    }

    /**
     * Function to quit the browser
     */
    public void closeBrowser(WebDriver driver) {

            driver.quit();


    }

}
