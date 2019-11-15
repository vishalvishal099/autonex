package com.oracle.babylon.Utils.setup.dataStore;

import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Project;

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


        String projectName = faker.app().name();
        project.setProjectName(projectName);
        project.setProjectShortName(projectName.substring(0, projectName.length() / 2));
        project.setProjectCode(projectName);
        project.setProjectType(faker.commerce().department());
        project.setPrimaryRegisterType(projectMap.get("Primary_Register_Type"));
        project.setDefaultAccessLevel(projectMap.get("Default_Access_Level"));
        project.setProjectAddress(faker.address().streetAddress());
        project.setCity(faker.address().city());
        project.setCounty(faker.address().state());
        project.setCountry(faker.address().country());
        project.setPostCode(faker.address().zipCode());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        project.setProjectStartDate(dateFormat.format(date));
        //Generate future date. Adding one year to current date
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 1);
        date = c.getTime();
        project.setEstimatedCompletionDate(dateFormat.format(date));
        project.setProjectValue(String.valueOf(faker.number().randomNumber()));
        project.setProjectDescription(faker.book().title());
        //Store the values in the data store in the project table
        new DataStore().storeProjectInfo("project", project);
    }
}
