package com.oracle.babylon.Utils.setup.dataStore;

import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Class to create the data for the project
 * Author : susgopal
 */
public class ProjectDataCreator {
    private Project project = new Project();
    private Faker faker = new Faker();

    /**
     * Function to create the project data using Faker class and set it to the Project pojo
     *
     * @param projectMap
     */
    public void generateProjectData(Map<String, String> projectMap) {

        ConfigFileReader configFileReader = new ConfigFileReader();
        String projectName = faker.app().name();
        projectName = projectName.replaceAll("[^a-zA-Z0-9]", "");
        project.setProjectName(projectName);
        project.setProjectShortName(projectName);
        project.setProjectCode(projectName);
        project.setProjectType(faker.commerce().department());
        project.setPrimaryRegisterType(projectMap.get("Primary_Register_Type"));
        project.setDefaultAccessLevel(projectMap.get("Default_Access_Level"));
        project.setProjectAddress(faker.address().streetAddress());
        project.setCity(faker.address().city());
        project.setCounty(faker.address().state());
        project.setCountry(configFileReader.getCountryName());
        project.setPostCode(faker.address().zipCode());
        project.setProjectStartDate("01/01/2016");
        project.setEstimatedCompletionDate("01/01/2025");
        project.setProjectValue(String.valueOf(faker.number().randomNumber()));
        project.setProjectDescription(faker.book().title());
        //Store the values in the data store in the project table
         new DataStore().storeProjectInfo("project", project);
    }
}
