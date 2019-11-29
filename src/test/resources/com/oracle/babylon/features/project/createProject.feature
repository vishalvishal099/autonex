Feature: Create a project and test scenarios related to Project


  Scenario: Create a project. Upload the project name and id in the User Data JSON
    When user "user1" login and create "project1"
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

