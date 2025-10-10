@sales @superset
Feature: Sales Dashboards Caseleri


  Background:
    When   The User opens the browser with the given url
    And   The User inputs a valid username "validUsername"
    And   The User inputs a valid password "validPassword"
    And   The User clicks the Submit button
    Then  The User waits until the Analysis element is visible with a timeout of 120 seconds


  Scenario: Region Manager Check name and mail
    When The user impersonate region manager
    When The user go to Sales Dashboard
    Then The user verify name and mail "PIRIL.ORHAN@diageo.com"

  Scenario: Sales Manager Check name and mail
    When The user impersonate sales manager
    When The user go to Sales Dashboard
    Then The user verify name and mail "emre.hacifazlioglu@diageo.com"

  Scenario: Field Manager Check name and mail
    When The user impersonate field manager
    When The user go to Sales Dashboard
    Then The user verify name and mail "Yavuz.Yesil@diageo.com"

  Scenario: Region Manager No Data Control
    When The user impersonate region manager
    When The user go to Sales Dashboard
    Then The user verify dashboard data 'Ekip'
    Then The user verify dashboard data 'Özet'
    Then The user verify dashboard data 'Gün İçi'
    Then The user verify dashboard data 'Detay'
    Then The user verify dashboard data 'Ziyaret'
    Then The user verify dashboard data 'Stok'

  Scenario: Sales Manager No Data Control
    When The user impersonate sales manager
    When The user go to Sales Dashboard
    Then The user verify dashboard data 'Ekip'
    Then The user verify dashboard data 'Özet'
    Then The user verify dashboard data 'Gün İçi'
    Then The user verify dashboard data 'Detay'
    Then The user verify dashboard data 'Ziyaret'
    Then The user verify dashboard data 'Stok'

  Scenario: Field Manager No Data Control
    When The user impersonate field manager
    When The user go to Sales Dashboard
    Then The user verify dashboard data 'Ekip'
    Then The user verify dashboard data 'Özet'
    Then The user verify dashboard data 'Gün İçi'
    Then The user verify dashboard data 'Detay'
    Then The user verify dashboard data 'Ziyaret'
    Then The user verify dashboard data 'Stok'

  Scenario: Region Manager Filter Check
    When The user impersonate region manager
    When The user go to Sales Dashboard
    When The user use filters region
    When The user click find button
    Then The user verify dashboard data 'Ekip'


  Scenario: Sales Manager Filter Check
    When The user impersonate sales manager
    When The user go to Sales Dashboard
    When The user use filters sales
    When The user click find button
    Then The user verify dashboard data 'Ekip'


  Scenario: Field Manager Filter Check
    When The user impersonate field manager
    When The user go to Sales Dashboard
    When The user use filters field
    When The user click find button
    Then The user verify dashboard data 'Ekip'
