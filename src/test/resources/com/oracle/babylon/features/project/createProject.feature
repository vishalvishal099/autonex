Feature: Create a project and test scenarios related to Project


  Scenario: Create a project. Upload the project name and id in the User Data JSON
    When we login and create project
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

