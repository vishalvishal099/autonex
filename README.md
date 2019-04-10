# Cucumber-Java-Poc

This is the simplest possible build script setup for Cucumber using Java and some selenide .
There is nothing fancy like a webapp or browser testing. All this does is to show you how
to install and run Cucumber!

There is a single feature file with one scenario. The scenario has three steps, two of them pending. See if you can make them all pass!

## Get the code

Git:

    git clone git@git.aconex.cloud:vsingh/cucumber-java-poc.git
    cd cucumber-java-poc


## Use Maven

Open a command window and run:

    mvn test

This runs Cucumber com.oracle.babylon.features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `RunCukesTest`
class tells JUnit to kick off Cucumber.

## Use Gradle

Open a command window and run:

    gradlew test --info
    
    ./gradlew  test 

we can run the specific task 

     ./gradlew cucumber  //cucumber is task name
         

This runs Cucumber com.oracle.babylon.features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `RunCukesTest`
class tells JUnit to kick off Cucumber.




## other options

The Cucumber runtime parses command line options to know what com.oracle.babylon.features to run, where the glue code lives, what plugins to use etc.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test.

Sometimes it can be useful to override these options without changing or recompiling the JUnit class. This can be done with the
`cucumber.options` system property. The general form is:

Using Maven:

    mvn -Dcucumber.options="..." test

Using Gradle:

    gradlew -Dcucumber.options="..." test

Let's look at some things you can do with `cucumber.options`. Try this:

    -Dcucumber.options="--help"

That should list all the available options.

*IMPORTANT*

When you override options with `-Dcucumber.options`, you will completely override whatever options are hard-coded in
your `@CucumberOptions` or in the script calling `cucumber.api.cli.Main`. There is one exception to this rule, and that
is the `--plugin` option. This will not _override_, but _add_ a plugin. The reason for this is to make it easier
for 3rd party tools (such as [Cucumber Pro](https://cucumber.pro/)) to automatically configure additional plugins by appending arguments to a `cucumber.properties`
file.

### Run a subset of Features or Scenarios

Specify a particular scenario by *line* (and use the pretty plugin, which prints the scenario back)

    -Dcucumber.options="classpath:src/test/resources/com/oracle/babylon/login.feature:4 --plugin pretty"

This works because Maven puts `./src/test/resources` on your `classpath`.
You can also specify files to run by filesystem path:

    -Dcucumber.options="src/test/resources/skeleton/login.feature:4 --plugin pretty"

You can also specify what to run by *tag*:

    -Dcucumber.options="--tags @bar --plugin pretty"

### Running only the scenarios that failed in the previous run

    -Dcucumber.options="@target/rerun.txt"

This works as long as you have the `rerun` formatter enabled.

### Specify a different formatter:

For example a JUnit formatter:

    -Dcucumber.options="--plugin junit:target/cucumber-junit-report.xml"


