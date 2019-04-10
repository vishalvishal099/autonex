package com.oracle.babylon.worldHelper.helper;

import com.oracle.babylon.worldHelper.Setup.utils.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class DriverFactory {

    ConfigFileReader configFileReader = new ConfigFileReader();

    public WebDriver browser() {
        switch (configFileReader.getBrowser().toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
                WebDriver chromeDriver = new ChromeDriver();
                configureDriver(chromeDriver);
                return chromeDriver;

            case "firefox":
                System.setProperty("tst", "tet");
                WebDriver firefoxDriver = new FirefoxDriver();
                configureDriver(firefoxDriver);
                return firefoxDriver;

            case "ie":
                System.setProperty("tst", "tet");
                WebDriver ieDriver = new InternetExplorerDriver();
                configureDriver(ieDriver);
                return ieDriver;
        }
        throw new RuntimeException(" Not such driver available, Please verify your driver !!!");
    }

    private void configureDriver( WebDriver driver) {
        driver.manage().window().maximize();
    }

}
