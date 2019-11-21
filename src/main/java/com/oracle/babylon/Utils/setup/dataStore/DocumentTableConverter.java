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
import java.util.Map;

/**
 * Class to convert the data tables from the test files to data store tables for a document
 * Author : susgopal
 */
public class DocumentTableConverter {
    private  Document document = new Document();

    public void createDocumentData(String name, DataTable dataTable) {
        //Object Initialization

        Map<String, String> documentHashMap = dataTable.transpose().asMap(String.class, String.class);
        FakeData fakeData = new FakeData();
        Date date = new Date();
        //Fetching data
        String companyName = fakeData.getCompanyName();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.0Z'");
        //Assigning values to Document Object
        document.setDocumentNumber(fakeData.getDocumentNumber());
        document.setAttribute1(documentHashMap.get("Attribute1"));
        document.setDocumentTypeId(Integer.parseInt(documentHashMap.get("Document_type_id")));
        document.setComments("Test comments for " + companyName + " document");
        document.setDocumentStatusId(Integer.parseInt(documentHashMap.get("Document_Status_Id")));
        document.setHasFile(Boolean.getBoolean(documentHashMap.get("HasFile")));
        document.setDiscipline(documentHashMap.get("Discipline"));
        document.setRevision(documentHashMap.get("Revision"));
        document.setTitle(companyName + " Doc");
        document.setRevisionDate(dateFormat.format(date));
        new DataStore().uploadDocument(name, document);
    }
}
