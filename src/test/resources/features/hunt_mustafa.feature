Feature: Hunt module

@wip
  Scenario Outline:  Verify the team lead or a teacher should be able to make a reservation
    Given user logs in using "<email>" "<password>"
    When user navigates to "hunt" page
    And user selects next day on calendar
#    Then status code should be 200
    Examples:
      | email                           | password      |
      | mcossor8l@webmd.com             | cecilnacey    |
#      | bmurkus8q@psu.edu               | alicasanbroke |
#      | teachervasctoforstall@gmail.com | scottforstall |
#      | teachervamikemarcus@gmail.com   | mikemarcus    |