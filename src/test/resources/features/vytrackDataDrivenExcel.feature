Feature: User Verification

  @excel
  Scenario Outline: verify information about logged user
    Given I go to the vytrack login page
    When I login with the <row_index> .  user cridentials
    Then verify <row_index> . user logged in successfully
    Examples:
      | row_index |
      | 1         |
      | 2         |
      | 3         |
      | 4         |
      | 5         |
      | 6         |
      | 7         |
      | 8         |
      | 9         |
      | 10        |
      | 11        |
      | 12        |
      | 13        |


