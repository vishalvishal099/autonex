Feature: Data setup for mail


  Scenario: Create a organization and store information into UserData.json for mail1
    When user "user1" tries to create org
    When user "user1" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |

  Scenario: Create a project. Upload the project name and id in the User Data JSON for mail1
    When user "user1" login and create "project1"
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

  Scenario: user creates a new user inside same org1
    Given 'user1' creates a "user2" in same org with "project1"
    When user "user2" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |

  Scenario: Create a organization and store information into UserData.json for mail2
    When user "user3" tries to create org
    When user "user3" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |

  Scenario: Create a project. Upload the project name and id in the User Data JSON for mail2
    When user "user3" login and create "project1"
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

  Scenario: user creates a new user inside same org2
    Given 'user3' creates a "user4" in same org with "project1"
    When user "user4" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |



  Scenario: Create a organization and store information into UserData.json for mail3
    When user "user5" tries to create org
    When user "user5" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |

  Scenario: Create a project. Upload the project name and id in the User Data JSON for mail3
    When user "user5" login and create "project1"
      | Primary_Register_Type | Default_Access_Level |
      | DOC                   | Normal               |

  Scenario: user creates a new user inside same org3
    Given 'user5' creates a "user6" in same org with "project1"
    When user "user6" needs to fill in the account details fields with data
      | Title     | Job_Function    | Job_Title  | Language                 |
      | Professor | Quality Control | Consultant | English (United Kingdom) |