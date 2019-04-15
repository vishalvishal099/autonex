Feature: Mail Test

  Background: Test data

    Given User Data "user1"
      | Fullname  | Username | Projects     | Organization    | password |
      | Eric Gibb | egibb    | Emerald Mine | Apex Mechanical | ac0n3x72 |

    Given User Data "user2"
      | Fullname     | Username | Projects     | Organization   |
      | Mark Perkins | mperkins | Emerald Mine | Rand Resources |

    Given Vertical Table "mail_attributes"
      | Mail Type   | Internal Memorandum       |
      | Subject     | Basic Mail Test           |
      | Attribute 1 | Doors                     |
      | Mail Body   | Send Mail feature testing |

  Scenario: Search Mail in inbox
    #Given "valid_user" login with correct username and password
    #Then user should logged into aconex
    #And User navigate to Mail-inbox page
    When "user1" search mail "WIR-TRANSMIT-000014" in inbox
    Then  user should see the mail "WIR-TRANSMIT-000014" in inbox


  Scenario: Sending sample mail
    When "user1" compose mail with "mail_attributes"
    When sent mail to "user2"
    Then verify "user2" has received mail