Feature: Login Test

  Background: Test data

    Given User Data "user1"
      | Fullname  | Username | Projects     | Organization    | password |
      | Eric Gibb | egibb    | Emerald Mine | Apex Mechanical | ac0n3x72 |

    Given User Data "invalid_user"
      | Fullname  | Username | Projects     | Organization    |
      | Eric Gibb | egibb123 | Emerald Mine | Apex Mechanical |

  Scenario: Search Mail in inbox
    #Given "valid_user" login with correct username and password
    #Then user should logged into aconex
    #And User navigate to Mail-inbox page
    When "user1" search mail "Mail_001" in inbox
    Then  user should see the mail