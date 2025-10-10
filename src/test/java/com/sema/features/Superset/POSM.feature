@regression @superset

Feature: Account Management Test Cases- Account Home Page
  Background:
    When   The User opens the browser with the given url
    And   The User inputs a valid username "validUsername"
    And   The User inputs a valid password "validPassword"
    And   The User clicks the Submit button
    Given  The user navigate to "https://diageo.efectura.com/Enrich/Items?itemType=Account"
    And   The User gets the current URL and stores it in "itemType=Account"

  Scenario:Account edit -POSM Tab
  And The user enters "999999999" into "Son Müşteri Kodu" filter text input box
    And  The user clicks on Edit Button
    And The user clicks "Önizleme" tab
    And The user clicks POSM button
    #And The user verifies POSM
