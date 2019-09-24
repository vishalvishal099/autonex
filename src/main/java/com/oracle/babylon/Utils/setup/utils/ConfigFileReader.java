package com.oracle.babylon.Utils.setup.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private String filepath = "src/main/resources/configFile.properties";

    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + filepath);
        }
    }

    public String getDriverPath() {
        String driverPath = properties.getProperty("DriverPath");
        if (driverPath != null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the configFile.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("ImplicitlyWaitTime");
        if (implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the CconfigFile.properties file.");
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("URL");
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the configFile.properties file.");
    }

    public String getPassword() {
        String password = properties.getProperty("PASSWORD");
        if (password != null) return password;
        else throw new RuntimeException("Password not specified in the configFile.properties file.");
    }


    public  String getBrowser(){
        String browser = properties.getProperty("BROWSER");
        if(browser !=null) return browser;
        else throw new RuntimeException("Browser is not specified in the configFile.properties file.");
    }

    public String getMode(){
        String mode = properties.getProperty("MODE");
        if(mode != null) return mode;
        else throw new RuntimeException("Mode is not specified in the configuration.propertied file");
    }
}


