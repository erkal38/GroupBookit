@smoke
Feature: User Verification


  Scenario: verify information about logged user
    Given I logged Bookit api using "sbirdbj@fc2.com" and "asenorval"
    When I get the current user information from api
    Then status code should be 200

   @db
  Scenario: verify information about logged user from api and database
    Given I logged Bookit api using "sbirdbj@fc2.com" and "asenorval"
    When I get the current user information from api
    Then the information about current user from api and database should be match

  @db
  Scenario: three point verification (UI,DATABASE,API)
    Given user logs in using "sbirdbj@fc2.com" "asenorval"
    When user is on the my self page
    And I logged Bookit api using "sbirdbj@fc2.com" and "asenorval"
    And I get the current user information from api
    Then UI,API and Database user information must be match


  @db
  Scenario Outline: three point verification (UI,DATABASE,API)
    Given user logs in using "<email>" "<password>"
    When user is on the my self page
    And I logged Bookit api using "<email>" and "<password>"
    And I get the current user information from api
    Then UI,API and Database user information must be match

    Examples:
      | email                      | password     |
      | sbirdbj@fc2.com            | asenorval    |
      | ccornil1h@usnews.com       | corniecornil |
      | efewtrell8c@craigslist.org | jamesmay     |

   @db
  Scenario Outline: three point verification (UI,DATABASE,API)
    Given user logs in UI BookIt by using <rowindex>. row credentials of Excel from "src/test/resources/BookIt.xlsx""Sheet1"
    And user logs in API BookIt  by using same credentials
    And user logs in DB BookIt  by using same credentials
    Then UI,API and DB user information must be match

    Examples:
      | rowindex |
      | 1        |
      | 2        |
      | 3        |
      | 4        |
      | 5        |
      | 6        |
      | 7        |
      | 8        |
      | 9        |
      | 10       |
      | 11       |
      | 12       |
      | 13       |