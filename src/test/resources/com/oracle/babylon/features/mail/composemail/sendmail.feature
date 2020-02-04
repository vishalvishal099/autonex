Feature: Send Mail

  Background: Test data

    Given User Data "user1"
      | Fullname  | Username | Projects     | Organization    |
      | Eric Gibb | egibb    | Breeze Tower | Apex Mechanical |

    Given User Data "user2"
      | Fullname     | Username | Projects     | Organization   |
      | Mark Perkins | mperkins | Breeze Tower | Rand Resources |

    Given User Data "user3"
      | Fullname   | Username | Projects     | Organization |
      | Ebru Ozcan | eozcan   | Breeze Tower | Arkitera     |

    Given User Data "user4"
      | Fullname         | Username | Projects     | Organization             |
      | Gretchen Pitcher | gpitcher | Breeze Tower | Ashton Design & Drafting |

    Given Vertical Table "mail_attributes1"
      | Mail Type   | Internal Memorandum       |
      | Subject     | Basic Mail Test           |
      | Attribute 1 | Administration            |
      | Attribute 2 | Innovation                |
      | Mail Body   | Send Mail feature testing |

    Given Vertical Table "mail_attributes2"
      | Mail Type   | Transmittal               |
      | Subject     | Basic Mail Test           |
      | Attribute 1 | Administration            |
      | Attribute 2 | Innovation                |
      | Mail Body   | Send Mail feature testing |

    Given Vertical Table "mail_attributes3"
      | To          | Mark Perkins,Lewis Miller       |
      | Cc          | Ebru Ozcan,Nicki Curtain        |
      | Bcc         | Gretchen Pitcher,Pardeep Haresh |
      | Mail Type   | Internal Memorandum             |
      | Subject     | Basic Mail Test                 |
      | Attribute 1 | Administration                  |
      | Attribute 2 | Innovation                      |
      | Mail Body   | Send Mail feature testing       |

    Given Vertical Table "mail_attributes4"
      | Mail Type   | Internal Memorandum |
      | To          | Mark Perkins        |
      | Subject     | Basic Mail Test     |
      | Attribute 1 | Administration      |
      | Attribute 2 | Innovation          |
      | Attachment  | ACONEXQA-2106       |
      | File Type   | png                 |

    Given Vertical Table "mail_attributes5"
      | Mail Type   | Transmittal               |
      | Subject     | Basic Mail Test           |
      | Attachment  | ACONEXQA-2106             |
      | Attribute 1 | Administration            |
      | Attribute 2 | Innovation                |
      | Mail Body   | Send Mail feature testing |


    #ACONEXQA-2105
  Scenario: Attaching document in simple mail
    Given "user1" set "See full search window for attaching documents from register" setting "false"
    Given "user1" have a mail with "mail_attributes1" in drafts
    Then user edits the email from draft and attaches "ACONEXQA-2106" document and sends saved mail to "user2"
    Then verify "user2" has received mail

    #ACONEXQA-2106 and #ACONEXQA-2117
  Scenario: Attaching document in transmittal,
  Recipient can view the document properties from the received transmittal
    Given "user1" have a mail with "mail_attributes2" in drafts
    Then user edits the email from draft and attaches "ACONEXQA-2106" document and sends saved mail to "user2"
    Then verify "user2" has received mail
    And verify document details on inbox page for "ACONEXQA-2106"

    #ACONEXQA-2107
  Scenario: Document Search for basic mail should always open in full screen, when preference is set
    Given "user1" set "See full search window for attaching documents from register" setting "true"
    Given "user1" have a mail with "mail_attributes1" in drafts
    Then user edits the email from draft and attaches "ACONEXQA-2106" document from full search and sends to "user2"
    Then "user1" set "See full search window for attaching documents from register" setting "false"

    #ACONEXQA-2108
  Scenario: Document Search for transmittal should always open in full screen, when preference is set
    Given "user1" set "See full search window for attaching documents from register" setting "true"
    Given "user1" have a mail with "mail_attributes2" in drafts
    Then user edits the email from draft and attaches "ACONEXQA-2106" document from full search and sends to "user2"
    Then "user1" set "See full search window for attaching documents from register" setting "false"


    #ACONEXQA-2109
  Scenario: Verify Mail should be received by only valid recipients
    Then "user1" set "See full search window for attaching documents from register" setting "false"
    Given "user1" have a mail with "mail_attributes3" in drafts
    When user edits the mail and removes "Ebru Ozcan" from "Cc"
    And user removes "Gretchen Pitcher" from "Bcc"
    Then user attaches "ACONEXQA-2106" in the mail and sends mail
    Then verify "user3,user4" has not received mail and "user2" has

    #ACONEXQA-2110
  Scenario: Verify Previewing a blank mail, saves it to drafts
    Given "user1" previews a blank mail
    Then mail will be present in the draft

    #ACONEXQA-2111 , #ACONEXQA-2112 and #ACONEXQA-2112
  Scenario: Verify to Preview a mail with only mandatory fields filled in,
  Verify to Preview a mail with most fields filled in,
  Verify the attached document type in Preview Mail and Open and Save options
    Given "user1" previews mail with "mail_attributes4"
    Then verify "mail_attributes4" in preview

    #ACONEXQA-2113 , #ACONEXQA-2114 and #ACONEX-2115
  Scenario: Verify no validation message displays in Preview Mail on attaching a doc without file,
  Verify validation message displays in New Mail on attaching a doc without file,
  Verify the Print button on Preview Mail
    Given "user1" have a mail with "mail_attributes1" in drafts
    Then user edits the email attaches "DocWithoutFile" document & verify message and no error message on preview
    Then user verify "print" button on preview page

    #ACONEXQA-2118
  Scenario: User can mark the mail as Unread from the received transmittal mail
    Given "user1" have a mail with "mail_attributes2" in drafts
    Then user edits the email from draft and attaches "ACONEXQA-2106" document and sends saved mail to "user2"
    Then verify "user2" has received mail
    And user mark mail as unread

    #ACONEXQA-2119
  Scenario: Start a workflow from the transmittal received with documents having associated files
    Given "user1" creates a mail with "mail_attributes5" and sends it to "Mark Perkins"
#    Then verify "user2" has received mail with "mail_attribute5"
#    Given "user1" have a mail with "mail_attributes5" in drafts
#    Then user edits the email from draft and attaches "ACONEXQA-2106" document and sends saved mail to "user2"
#    Then verify "user2" has received mail with "mail_attribute5"







