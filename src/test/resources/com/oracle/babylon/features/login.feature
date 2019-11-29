Feature: Login Test

  Background: Test data

    Given User Data "valid_user"
      | Fullname        | Username | Projects  | Organization      |
      | Patrick O'Leary | poleary  | Hotel VIP | Majestic Builders |

    Given User Data "invalid_user"
      | Fullname  | Username | Projects     | Organization    |
      | Eric Gibb | egibb0   | Emerald Mine | Apex Mechanical |



  Scenario: Successful Login
    Given "valid_user" login with correct username and password
    Then user should logged into aconex

  Scenario: Failed Login
    Given "invalid_user" login with incorrect username and password
    Then user should not logged into aconex

    #Scenario: JIRA Details
     # Given "ACONEXQA-568", retrieve details

