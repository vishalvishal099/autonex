package com.oracle.babylon.jobRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

//import org.testng.annotations.DataProvider;

//import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/oracle/babylon/features/mail/",
        tags = {"not @ignore"},
        glue = {"com.oracle.babylon"},
        plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/cucumber.json" },
        monochrome = true
)

public class MailTest{

    /**@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread ID--->" + threadId);
        return super.scenarios();
    }*/



}

