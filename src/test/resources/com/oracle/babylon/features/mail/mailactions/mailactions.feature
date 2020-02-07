Feature: Verifying the Mails in 'To' or 'CC' or 'BCC' recipients

  Background: Test data

    Given Vertical Table "mail_attributes"

      | Mail Type   | Internal Memorandum       |
      | Subject     | Basic Mail Test           |
      | Mail Body   | Send Mail feature testing |

  #ACONEXQA-1004
  Scenario: Sending sample mail with To and Cc Recipients
    When "user21" compose mail with "mail_attributes"
    And sends mail to user "user23" in tolist and user "user22" in cclist
    Then verify user "user23" for "to" option and user "user22" for "cc" option
    And verify user "user23" and "user22" for "any" option

