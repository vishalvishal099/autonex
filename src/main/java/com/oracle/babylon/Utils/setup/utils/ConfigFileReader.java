package com.oracle.babylon.Utils.setup.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Class to read the values of the config file and use it in the framework
 */
public class ConfigFileReader {

    private Properties properties;
    private String confileFilePath = "src/main/resources/configFile.properties";

    /**
     * Load the data from the config file and make it accessible to the Properties variable
     */
    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(confileFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + confileFilePath);
        }
    }

    //Each field in the config file has a respective Getter method
    public String getDriverPath() {
        String driverPath = properties.getProperty("DriverPath");
        if (driverPath != null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the configFile.properties file.");
    }

    public String getProxyURL() {
        String proxyUrl = properties.getProperty("PROXY_URL");
        if (proxyUrl != null) return proxyUrl;
        else throw new RuntimeException("Proxy URL not specified in the configFile.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("ImplicitlyWaitTime");
        if (implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the CconfigFile.properties file.");
    }

    public boolean getHttpProxySetStatus() {
        String setProxy = properties.getProperty("HTTP_PROXY");
        if (setProxy != null && setProxy.equals("true")) return true;
        else return false;
    }

    public boolean getAPIProxySetStatus() {
        String setProxy = properties.getProperty("API_PROXY");
        if (setProxy != null && setProxy.equals("true")) return true;
        else return false;
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("URL");
        if (url != null) return url;
        else throw new RuntimeException("Url not specified in the configFile.properties file.");
    }

    public String getPassword() {
        String password = properties.getProperty("PASSWORD");
        if (password != null) return password;
        else throw new RuntimeException("Password not specified in the configFile.properties file.");
    }


    public String getBrowser() {
        String browser = properties.getProperty("BROWSER");
        if (browser != null) return browser;
        else throw new RuntimeException("Browser is not specified in the configFile.properties file.");
    }

    public String getMode() {
        String mode = properties.getProperty("MODE");
        if (mode != null) return mode;
        else throw new RuntimeException("Mode is not specified in the configuration.propertied file");
    }

    public String getJiraUrl() {
        String jira_url = properties.getProperty("JIRA_URL");
        if (jira_url != null) return jira_url;
        else throw new RuntimeException("JIRA URL is not specified in the configuration.propertied file");
    }

    public String getAdminUsername() {
        String admin_username = properties.getProperty("ADMIN_USERNAME");
        if (admin_username != null) return admin_username;
        else throw new RuntimeException("Admin Username is not specified in the configuration.propertied file");
    }

    public String getEmailId() {
        String email_id = properties.getProperty("EMAIL");
        if (email_id != null) return email_id;
        else throw new RuntimeException("Email is not specified in the configuration.properties file");
    }

    public String getUserDataJsonFilePath() {
        String filePath = System.getProperty("user.dir") + properties.getProperty("USER_DATA_JSON");
        if (filePath != null) return filePath;
        else throw new RuntimeException("File Path is not specified in the configuration.properties file");
    }
    public String getDocumentDataJsonFilePath() {
        String filePath = System.getProperty("user.dir") + properties.getProperty("DOCUMENT_DATA_JSON");
        if (filePath != null) return filePath;
        else throw new RuntimeException("File Path is not specified in the configuration.properties file");
    }

    public String getMailDataJsonFilePath() {
        String filePath = System.getProperty("user.dir") + properties.getProperty("MAIL_DATA_JSON");
        if (filePath != null) return filePath;
        else throw new RuntimeException("File Path is not specified in the configuration.properties file");
    }

    public String getSSOAuthString() {
        String sso_auth_string = properties.getProperty("SSO_Auth_String");
        if (sso_auth_string != null) return sso_auth_string;
        else throw new RuntimeException("SSO Auth String not specified in the configuration.properties file");

    }

    public Boolean getUseJsonFileFlag() {
        String use_json_file = properties.getProperty("USE_JSON_FILE");
        if (use_json_file.equals("true")) return true;
        else return false;
    }

    public String getJiraExecutionUrl(){
        String jira_exec_url = properties.getProperty("ZEPHYR_EXECUTION_URL");
        if(jira_exec_url != null) return jira_exec_url;
        else return null;
    }

    public String getJiraIssueUrl(){
        String jira_issue_url = properties.getProperty("JIRA_ISSUE_URL");
        if(jira_issue_url != null) return jira_issue_url;
        else return null;
    }

    public String getCountryName(){
        String country_name = properties.getProperty("COUNTRY_NAME");
        if(country_name != null) return country_name;
        else return null;
    }
}



