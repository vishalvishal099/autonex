Feature: Document upload, search and update tests

  Background: Test data
    #Document ID, Title and Revision date are auto generated and added into the data table
    Given Upload Document Data "uploadDoc_data"
      | Document_type_id | Revision | Attribute1 | HasFile | Comments             | Discipline | Document_Status_Id |
      | 1879048197       | A        | Airport    | false   | Uploading a document | Structural | 1879048196         |



  Scenario: Uploading a document through Registration
    Given upload document with data "uploadDoc_data"
    When search document for user "user_proj_table"
    Then verify if document is present
