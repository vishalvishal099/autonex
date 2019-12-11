package com.oracle.babylon.Utils.setup.dataStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to handle JSON operations while reading from JSON files
 * Author : susgopal
 */
public class DataSetup {
    /**
     * Converts JSON file data to a Map of Map
     *
     * @param filePath path of the JSON file present in the main/resources package
     * @return Map of Map containing the JSON data
     * @throws IOException
     * @throws ParseException
     */
    public Map<String, Map<String, String>> loadJsonDataToMap(String filePath){
        /** 1.Read the file and store it in an Object
         * 2. Convert Object to JSON Object
         * 3. Retrieve the keys in the JSON Object
         * 4. If JSON key's value is a JSON, then store the map as the value
         * 5. If JSON key's value is a String, then store the key and the value under the key firstlevel. Can be changed accordingly.
         */
        try {
            Object obj = new JSONParser().parse(new FileReader(filePath));
            Map<String, Map<String, String>> mapOfMap = new HashMap<>();

            JSONObject completeJson = (JSONObject) obj;
            Set<String> keyList = new HashSet<String>();
            keyList = completeJson.keySet();

            for (String key : keyList) {
                if (completeJson.get(key).getClass().equals(JSONObject.class)) {
                    mapOfMap.put(key, (HashMap) completeJson.get(key));
                } else if (completeJson.get(key).getClass().equals(String.class)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(key, completeJson.get(key).toString());
                    mapOfMap.put("firstLevel", map);
                }
            }
            return mapOfMap;
        } catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Function to overwrite a specific key's value in JSON
     *
     * @param keyList  keys to parse to reach the specific key provided
     * @param value    the value to be updated in the JSON file
     * @param filePath path  the JSON file present thin the main/resources package
     * @throws IOException
     * @throws ParseException
     */
    public void writeIntoJson(String[] keyList, String value, String filePath) {
        //Fetch the json from the file
        try {
            Object obj = new JSONParser().parse(new FileReader(filePath));
            JSONObject completeJson = (JSONObject) obj;
            String originalString = null, replaceString = null;

            //Get the key  and the value to be updated
            for (String key : keyList) {
                if (completeJson.get(key).getClass().equals(JSONObject.class)) {
                    completeJson = (JSONObject) completeJson.get(key);
                } else if (completeJson.get(key).getClass().equals(String.class)) {
                    originalString = "\"" + key + "\":\"" + completeJson.get(key) + "\"";
                    break;
                }
            }

            String jsonString = obj.toString();
            //Create the replacement string
            replaceString = "\"" + keyList[keyList.length - 1] + "\":\"" + value + "\"";
            //Replace the original pattern with the new one
            jsonString = jsonString.replace(originalString, replaceString);

            //Create the json in readable pretty print format
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonString);
            String prettyJsonString = gson.toJson(je);

            //Write the complete string to the file
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyJsonString);
            pw.flush();
            pw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
