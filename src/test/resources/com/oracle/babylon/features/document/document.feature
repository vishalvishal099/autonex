Feature: Document upload, search and update tests

  Scenario: Uploading a document through Registration
    Given upload document for user "user1" with data "uploadDoc_data" and write it in userData.json
      | Document_type_id | Revision | Attribute1 | HasFile | Comments             | Discipline | Document_Status_Id |
      | 1879048197       | A        | Airport    | false   | Uploading a document | Structural | 1879048196         |
    When search document for user "user1"
    Then verify if document is present

    Scenario: Multiple file upload
      Given upload Multiple files