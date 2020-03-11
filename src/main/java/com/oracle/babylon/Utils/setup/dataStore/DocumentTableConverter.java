package com.oracle.babylon.Utils.setup.dataStore;

import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.setup.FakeData;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Document;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import io.cucumber.datatable.DataTable;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class to convert the data tables from the test files to data store tables for a document
 * Author : susgopal
 */
public class DocumentTableConverter {

    public void createDocumentData(DataTable dataTable) {
        //Object Initialization
        for(Map<Object, Object> documentHashMap : dataTable.asMaps(String.class, String.class)) {
            Document document = new Document();
            //Map<String, String> documentHashMap = dataTable.transpose().asMap(String.class, String.class);
            FakeData fakeData = new FakeData();
            Date date = new Date();
            //Fetching data
            String companyName = fakeData.getCompanyName();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.0Z'");
            //Assigning values to Document Object
            document.setDocumentNumber(fakeData.getDocumentNumber());
            document.setComments("Test comments for " + companyName + " document");
            document.setHasFile(documentHashMap.get("HasFile").toString());
            String fileFlag = documentHashMap.get("HasFile").toString();
            boolean fileFlagBoolean = Boolean.parseBoolean(fileFlag);
            if (fileFlagBoolean == true) {
                document.setFileToUpload(documentHashMap.get("FileToUpload").toString());
            }
            document.setConfidentialityFlag(documentHashMap.get("Confidentiality").toString());
            document.setRevision(documentHashMap.get("Revision").toString());
            document.setTitle(companyName + " Doc");
            document.setRevisionDate(dateFormat.format(date));
            new DataStore().uploadDocument("document"+documentHashMap.get("serial num"), document);
        }
    }
}
