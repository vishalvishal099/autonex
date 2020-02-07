package com.oracle.babylon.Utils.helper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

public class ZephyrStatusSingleton {

    private static ZephyrStatusSingleton zephyrStatusSingleton;
    static int PASS_STATUS;
    static int FAIL_STATUS;

    private ZephyrStatusSingleton(String jiraId){
        JIRAOperations jiraOperations =  new JIRAOperations();
        String issueId = jiraOperations.getJiraId(jiraId);
        Map<String, Integer> map = jiraOperations.returnExecutionStatusId(issueId);
        PASS_STATUS = map.get("PASS");
        FAIL_STATUS = map.get("FAIL");
    }

    public static ZephyrStatusSingleton getInstance(String jiraId){
        if(zephyrStatusSingleton == null){
            zephyrStatusSingleton = new ZephyrStatusSingleton(jiraId);
        }
        return zephyrStatusSingleton;
    }


}