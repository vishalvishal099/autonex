package com.oracle.babylon.Utils.helper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class containing methods to perform Schema operations
 * Author : susgopal
 */
public class SchemaHelperPage {

    /**
     * Method that does the following operations
     * 1. Convert XML String to JSON data
     * 2. Parse the JSON and fetch Identifier and its schema values from EntityCreationSchemaFields element
     * 3. Store the values in a list and return it
     * @param xmlString xml value of HttpResponse
     * @param identifier key to be searched
     * @param returnValue value identifier to be returned
     * @return
     */
    public List<String> retrieveValuesFromSchema(String xmlString, String identifier, String returnValue) {

        JSONObject jsonObject = XML.toJSONObject(xmlString);
        Iterator<String> keys = jsonObject.keys();
        JSONArray jsonArray = new JSONArray();
        String key = null;
        if(keys.hasNext()){
            key = keys.next();
            jsonObject = (JSONObject)jsonObject.get(key);
        }
        keys = jsonObject.keys();
        while(keys.hasNext()){
            key = keys.next();
            if(key.equals("EntityCreationSchemaFields")){
                jsonObject = (JSONObject)jsonObject.get(key);
                break;
            }
        }
        jsonArray = (JSONArray)jsonObject.get("MultiValueSchemaField");

        for(int arrayCounter=0 ; arrayCounter<jsonArray.length(); arrayCounter++){
            jsonObject = (JSONObject) jsonArray.get(arrayCounter);
            if(jsonObject.get("Identifier").toString().equals(identifier)){
                jsonObject = (JSONObject)jsonObject.get("SchemaValues");
                if(jsonObject.get("SchemaValue").getClass().equals(JSONArray.class)){
                    jsonArray = (JSONArray)jsonObject.get("SchemaValue");
                }
                else{
                    jsonArray = new JSONArray();
                    jsonArray.put(jsonObject.get("SchemaValue"));
                }

                break;
            }
        }
        List<String> listOfValues = new ArrayList<>();
        for(int arrayCounter=0;arrayCounter<jsonArray.length();arrayCounter++){
            jsonObject = (JSONObject) jsonArray.get(arrayCounter);
            listOfValues.add(jsonObject.get(returnValue).toString());

        }


        return listOfValues;
    }


}
