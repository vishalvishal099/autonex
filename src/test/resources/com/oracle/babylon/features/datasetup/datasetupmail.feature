Feature: Data setup for mail

  @create_org
  @data_setup
  Scenario: Create a mail organization and store information into UserData.json
    When user "muser1" tries to create "morganization1"
#    Then user "muser1" is able to login to application

  @fill_acnt_details
  @data_setup
  Scenario: Fill up the account details for mail
    When user "muser1" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |

  @create_project
  @data_setup
  Scenario: Create a project. Upload the project name and id in the User Data JSON for mail test
    When user "muser1" login and create "mproject1"
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

  Scenario: user creates a new user inside same org
    Given 'muser1' creates a "muser2" in same org with "mproject1"
    When user "muser2" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |


  Scenario: Create a mail organization and user3 then store in the data file
    When user "muser3" tries to create "morganization2"
    Then user "muser3" is able to login to application
    When user "muser3" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  |
      | Professor | Quality Control | Consultant |

  Scenario: Create a mail organization and user4 then store in the data file
    When user "muser4" tries to create "morganization3"
    Then user "muser4" is able to login to application
    When user "muser4" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  |
      | Professor | Quality Control | Consultant |

  Scenario: Create a mail organization and user4 then store in the data file
    When user "muser5" tries to create "morganization4"
    Then user "muser5" is able to login to application
    When user "muser5" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  |
      | Professor | Quality Control | Consultant |






