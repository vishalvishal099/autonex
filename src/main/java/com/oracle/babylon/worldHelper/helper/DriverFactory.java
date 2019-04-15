package com.oracle.babylon.worldHelper.helper;

import com.oracle.babylon.worldHelper.setup.utils.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class DriverFactory {

    ConfigFileReader configFileReader = new ConfigFileReader();

    public WebDriver getDriver() throws MalformedURLException {
        WebDriver driver = null;
        if (configFileReader.getMode().equalsIgnoreCase("grid")) {
            driver = remoteDriver();
        } else {
            driver = browser();
        }
        return driver;
    }


    public WebDriver remoteDriver() throws MalformedURLException {
        switch (configFileReader.getBrowser().toLowerCase()) {

            case "chrome":
                DesiredCapabilities capability = new DesiredCapabilities().chrome();
                WebDriver chromDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
                configureDriver(chromDriver);
                return chromDriver;

            case "firefox":

                DesiredCapabilities ffCapability = new DesiredCapabilities().firefox();
                WebDriver firefoxDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), ffCapability);
                configureDriver(firefoxDriver);
                return firefoxDriver;

            case "ie":

                DesiredCapabilities IEcapability = new DesiredCapabilities().internetExplorer();
                WebDriver IEDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), IEcapability);
                configureDriver(IEDriver);
                return IEDriver;
        }
        throw new RuntimeException(" Remote driver not found available, Please verify your driver !!!");
    }


    public WebDriver browser() {
        switch (configFileReader.getBrowser().toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
                WebDriver chromeDriver = new ChromeDriver();
                configureDriver(chromeDriver);
                return chromeDriver;

            case "firefox":
                System.setProperty("webdriver.firefox.driver", configFileReader.getDriverPath());
                WebDriver firefoxDriver = new FirefoxDriver();
                configureDriver(firefoxDriver);
                return firefoxDriver;

            case "ie":
                System.setProperty("webdriver.ie.driver", configFileReader.getDriverPath());
                WebDriver ieDriver = new InternetExplorerDriver();
                configureDriver(ieDriver);
                return ieDriver;
        }
        throw new RuntimeException(" Not such driver available, Please verify your driver !!!");
    }

    private void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
    }

}
