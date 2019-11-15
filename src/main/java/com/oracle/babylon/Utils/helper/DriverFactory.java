package com.oracle.babylon.Utils.helper;

import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to handle the operations related to the driver
 * Author : vsinghsi, susgopal
 */
public class DriverFactory {
    //Initialization of objects and assigning references to the object.
    ConfigFileReader configFileReader = new ConfigFileReader();

    /**
     * Function to create a browser
     *
     * @return browser reference
     * @throws MalformedURLException
     */
    public WebDriver getDriver() throws MalformedURLException {
        WebDriver driver = null;
        if (configFileReader.getMode().equalsIgnoreCase("grid")) {
            driver = remoteDriver();
        } else {
            driver = browser();
        }
        return driver;
    }

    /**
     * Function to create a remote driver
     *
     * @return browser reference
     * @throws MalformedURLException
     */
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

    /**
     * Function to check for the os, browser and select the appropriate driver
     * @return respective browser references according to the required properties
     */
    public WebDriver browser() {
        /**
         * Using the OS and the required browser by using the conditional statements, to return the browser reference
         */
        String os = System.getProperty("os.name");
        String driverPath = System.getProperty("user.dir") + configFileReader.getDriverPath();
        if (os.contains("Windows") && configFileReader.getBrowser().toLowerCase().equals("chrome")) {
            driverPath = driverPath + "win64/chromedriver.exe";
            return setPropertyAndInitChromeDriver(driverPath);
        } else if (os.contains("Windows") && configFileReader.getBrowser().toLowerCase().equals("firefox")) {
            driverPath = driverPath + "win64/geckodriver.exe";
            return setPropertyAndInitFirefoxDriver(driverPath);
        } else if (os.contains("Windows") && configFileReader.getBrowser().toLowerCase().equals("ie")) {
            driverPath = driverPath + "win64/geckodriver.exe";
            return setPropertyAndInitIEDriver(driverPath);
        } else if (os.contains("Mac") && configFileReader.getBrowser().toLowerCase().equals("chrome")) {
            driverPath = driverPath + "mac64/chromedriver";
            return setPropertyAndInitChromeDriver(driverPath);
        } else if (os.contains("Mac") && configFileReader.getBrowser().toLowerCase().equals("firefox")) {
            driverPath = driverPath + "mac64/geckodriver";
            return setPropertyAndInitFirefoxDriver(driverPath);
        }
        throw new RuntimeException(" Not such driver available, Please verify your driver !!!");
    }

    /**
     * Function to configure the properties for Chrome browser
     * @param path
     * @return browser reference
     */
    private WebDriver setPropertyAndInitChromeDriver(String path) {
        WebDriver chromeDriver = null;
        System.setProperty("webdriver.chrome.driver", path);
        //Setting the proxy for the driver by getting the configuration from configFile.properties
        if (configFileReader.getProxySetStatus()) {
            ChromeOptions options = new ChromeOptions();
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(configFileReader.getProxyURL())
                    .setFtpProxy(configFileReader.getProxyURL())
                    .setSslProxy(configFileReader.getProxyURL());
            options.setCapability("proxy", proxy);
            chromeDriver = new ChromeDriver(options);

        } else {
            chromeDriver = new ChromeDriver();
        }
        configureDriver(chromeDriver);
        return chromeDriver;
    }

    /** Function to configure the properties of Firefox browser
     * @param path
     * @return browser reference
     */
    private WebDriver setPropertyAndInitFirefoxDriver(String path) {
        //For latest version of firefox we need to configure the gecko driver
        System.setProperty("webdriver.gecko.driver", path);
        WebDriver firefoxDriver = null;
        if (configFileReader.getProxySetStatus()) {
            FirefoxOptions options = new FirefoxOptions();
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(configFileReader.getProxyURL());
            options.setCapability("proxy", proxy);
            firefoxDriver = new FirefoxDriver(options);
        } else {
            firefoxDriver = new FirefoxDriver();
        }

        configureDriver(firefoxDriver);
        return firefoxDriver;
    }

    /**
     * Function to configure the properties of Internet Explorer browser
     * @param path
     * @return reference to the ie driver
     */
    private WebDriver setPropertyAndInitIEDriver(String path) {
        System.setProperty("webdriver.ie.driver", path);
        WebDriver ieDriver = new InternetExplorerDriver();
        configureDriver(ieDriver);
        return ieDriver;
    }

    /**
     * Function to make some changes to the object / reference
     * @param driver
     */
    private void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
    }

}
