@regression @superset
Feature: Cocpit General Dashboard
  Background:
    When   The User opens the browser with the given url
    And   The User inputs a valid username "validUsername"
    And   The User inputs a valid password "validPassword"
    And   The User clicks the Submit button
    Then  The User waits until the Analysis element is visible with a timeout of 120 seconds
    Given The user is on the Analysis cocpit

  Scenario:Cocpit Overview tab Verify
    When  The user cocpit page verifies

  Scenario:Cocpit Network Tab Verify
    When  The user network page verifies

  Scenario:Cocpit Call Center Tab Verify
    When  The user call center page verifies

  Scenario:Cocpit Marketing Tab Verify
    When  The user marketing page verifies

  Scenario:Cocpit Details Tab Verify
    When  The user details page verifies