Feature: Preferences Test

  Background: Test data
    Given User Data "admin"
      | Fullname                         | Username  | Organization                 |
      | Admin (Do not Use) Administrator | acnxAdmin | Demo Instance Administration |

  #ACONEXQA-2472
  Scenario: Mail Document Role Setting3
    Given aconexadmin logs in to default preference page
    Then user selects "English (United Kingdom)" with use default setting checked
    Then user creates a new organisation
