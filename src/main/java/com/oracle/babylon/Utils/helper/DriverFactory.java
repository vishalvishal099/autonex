package com.oracle.babylon.Utils.helper;

import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
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
        /**
         * Check for the os, browser and select the appropriate driver
         */
        String os = System.getProperty("os.name");
        String driverPath = System.getProperty("user.dir") + configFileReader.getDriverPath();
        if(os.contains("Windows") && configFileReader.getBrowser().toLowerCase().equals("chrome")){
            driverPath = driverPath + "win64/chromedriver.exe" ;
            return setPropertyAndInitChromeDriver(driverPath);
        } else if(os.contains("Windows") && configFileReader.getBrowser().toLowerCase().equals("firefox")){
            driverPath = driverPath + "win64/geckodriver.exe" ;
            return setPropertyAndInitFirefoxDriver(driverPath);
        } else if(os.contains("Windows") && configFileReader.getBrowser().toLowerCase().equals("ie")){
            driverPath = driverPath + "win64/geckodriver.exe" ;
            return setPropertyAndInitIEDriver(driverPath);
        } else if(os.contains("Mac") && configFileReader.getBrowser().toLowerCase().equals("chrome")){
            driverPath = driverPath + "mac64/chromedriver" ;
            return setPropertyAndInitChromeDriver(driverPath);
        } else if(os.contains("Mac") && configFileReader.getBrowser().toLowerCase().equals("firefox")){
            driverPath = driverPath + "mac64/geckodriver" ;
            return setPropertyAndInitFirefoxDriver(driverPath);
        }
        throw new RuntimeException(" Not such driver available, Please verify your driver !!!");
    }

    private WebDriver setPropertyAndInitChromeDriver(String path){
        System.setProperty("webdriver.chrome.driver", path);
        WebDriver chromeDriver = new ChromeDriver();
        configureDriver(chromeDriver);
        return chromeDriver;
    }

    private WebDriver setPropertyAndInitFirefoxDriver(String path){
        System.setProperty("webdriver.chrome.driver", path);
        WebDriver firefoxDriver = new FirefoxDriver();
        configureDriver(firefoxDriver);
        return firefoxDriver;
    }

    private WebDriver setPropertyAndInitIEDriver(String path){
        System.setProperty("webdriver.ie.driver", path);
        WebDriver ieDriver = new InternetExplorerDriver();
        configureDriver(ieDriver);
        return ieDriver;
    }



    private void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
    }

}
