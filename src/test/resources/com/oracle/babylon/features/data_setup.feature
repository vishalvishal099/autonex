Feature: Essential tests which needs to be tested and also involves data setup in a server

  @create_org
  @data_setup
  Scenario: Create a organization and store information into UserData.json
    When user "user1" tries to create org
    Then user "user1" is able to login to application

  @fill_acnt_details
  @data_setup
  Scenario: Fill up the account details
    When user "user1" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |
    Then views the home page

  @create_project
  @data_setup
  Scenario: Create a project. Upload the project name and id in the User Data JSON
    When user "user1" login and create "project1"
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

  @enable_webservices_api
  @data_setup
  Scenario: Login to the admin and enable the web services api checkbox
    When Login and set the web services api checkbox for user "user1" and project "project1"
    Then verify if feature changes save is successful

  @lock_fields_documents
  @data_setup
  Scenario: Login to the server and lock the fields for the document
    When Login and lock the documents fields for user "user1" and project "project1"
    Then verify if lock fields is disabled

  @add_doc_attribute
  @data_setup
  Scenario: Add document attribute and write the attribute to userData.json
    When Login for user "user1" and project "project1", add a document attribute "Attribute 1"
    Then Write attribute for "document1" in documents file

  @upload_document
  @data_setup
  Scenario: Uploading a document through Registration
  #  Given upload document for user "user1" for project "project1" with data "uploadDoc_data" and write to "document1"
  #    | Revision | HasFile | Comments             |
   #   | A        | false   | Uploading a document |
    When search document "document1" for user "user1" and project "project1"
    Then verify if document "document1" is present

  @add_mail_attribute
  @data_setup
  Scenario: Add mail attribute and write the attribute to userData.json
    When Login for user "user1" and add a mail attribute "Attribute 1"
    Then Write "Attribute1" for "Mail" in userData.json

  @create_transmittal
  @data_setup
  Scenario: Create a transmittal for a document
    When Login for user "user1" and create a mail of type transmittal, send to user
      | Full_Name    | Mail_Attribute | Comments                     |
      | Mark Perkins | default        | Creating a basic transmittal |
    Then verify if "Transmittal" is created

  @data_setup
  Scenario: Uploading documents
    Given upload document for user "user1" for project "project1" and write to "document.json"
      | serial num | Revision | HasFile | Comments             | FileToUpload     | Confidentiality |
      | 1          | A        | true    | Uploading a document | pdf1.pdf         | No              |
      | 2          | A        | true    | Uploading a document | TestTextfile.txt | yes             |
      | 3          | A        | false   | Uploading a document | TestTextfile.txt | yes             |
   # When search document "document1" for user "user1" and project "project1"
   # Then verify if document "document1" is present


