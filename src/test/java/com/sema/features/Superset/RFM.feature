@regression @superset

Feature: Cocpit General Dashboard
  Background:
    When   The User opens the browser with the given url
    And   The User inputs a valid username "validUsername"
    And   The User inputs a valid password "validPassword"
    And   The User clicks the Submit button
    Then  The User waits until the Analysis element is visible with a timeout of 120 seconds
    Given The user is on the RFM

  Scenario:RFM Overview  Verify
    When  The user rfm page verifies