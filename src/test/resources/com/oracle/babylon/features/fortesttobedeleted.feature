Feature: Mail Test

  Background: Test data
    Given User Data "user1"
      | Fullname        | Username | Projects  | Organization      | Password |
      | Patrick O'Leary | poleary  | Hotel VIP | Majestic Builders | ac0n3x72 |

    Given Vertical Table "project_attributes"
      | Project Type | Construction |

    Given Vertical Table "role_param"
      | Mail Types   | Variation    |
      | Doc Types    | Agenda       |
      | Doc Statuses | For Approval |


  @search_mail
  Scenario Outline: Mail Document Role Setting1
    When user "user1" logs in to mail doc role setting
    Then user navigates to project tab "<role_type>" and "<role_name>" with "role_param"
    And user verify rolename "<role_name>"
    Examples:
      | role_type | role_name    |
      | default   | Default Role |
      | normal    | Normal Role  |

  Scenario Outline: Mail Document Role Setting2
    When user "user1" logs in to mail doc role setting
    Then user deletes role "<role_name>"
    Examples:
      | role_name      |
      | Statutory Body |

  Scenario: Mail Document Role Setting3
    When user "user1" logs in to mail doc role setting
    Then user deselect all org from role role "Hello"

  Scenario: Document Field1
    When user "user1" logs in to docfield tab
    Then user select use field for "Reference" as "yes"

  Scenario: Document Field2
    When user "user1" logs in to docfield tab
    Then user select mandatory for "Reference" as "yes"

  Scenario: Document Field3
    When user "user1" logs in to docfield tab
    Then user select editable inline for "Discipline" as "yes"

  Scenario: Document Field4
    When user "user1" logs in to docfield tab
    Then user edit list value for "Type"

#    And user copy role from project