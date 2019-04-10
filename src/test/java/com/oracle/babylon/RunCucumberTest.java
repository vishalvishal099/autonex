package com.oracle.babylon;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/oracle/babylon/",
        glue= {"com.oracle.babylon"},
        plugin = { "pretty", "html:target/cucumber-reports" },
        monochrome = true
)

public class RunCucumberTest {
}
