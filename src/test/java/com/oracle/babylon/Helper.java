package com.oracle.babylon;


import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.worldHelper.Setup.utils.ConfigFileReader;
import com.oracle.babylon.worldHelper.helper.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Helper {
    ConfigFileReader configFileReader = new ConfigFileReader();
    DriverFactory driverFactory = new DriverFactory();

    @Before
    public void driverSetup() {
        WebDriver driver = driverFactory.browser();
        WebDriverRunner.setWebDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }
        closeBrowser();
    }

    public void closeBrowser() {
        WebDriverRunner.getWebDriver().close();
    }

}
