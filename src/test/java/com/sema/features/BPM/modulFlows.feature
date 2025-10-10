@flow @modul-flow
Feature: Module Flows Cases

  Scenario: Plain Modul Made Flow Without Reject or Revise
    Given The user get blocked budget
    Given The user get actual budget
    Given The user login with "sahamuduru"
    Given The user go to "panel" page
    Given The user go in "Modül Akışı" flow
    Given The user select "Modül Yapım" as form type
    Given The user fill start form with musteri code "999999999"
    Given The user verify blocked budget with "10000"
    Given The user login with "satismuduru"
    Given The user go in Task and submit
    Given The user login with "offtrademanager"
    Given The user go in Task "DIA: ConfirmationForm"
    Given The user change distributor help
    Given The user verify blocked budget with "0"
    Given The user change distributor help
    Given The user verify blocked budget with "10000"
    Given The user submit the task
    Given The user login with "Tedarikçi"
    Given The user fill vendor form with initial budget "5000"
    Given The user login with "sahamuduru"
    Given The user go in Task and submit
    Given The user verify blocked budget with "5000"
    Given The user login with "satismuduru"
    Given The user go in Task and submit
    Given The user login with "bolgemuduru"
    Given The user go in Task and submit
    Given The user login with "offtrademanager"
    Given The user go in Task and submit
    Given The user login with "Tedarikçi"
    Given The user fill vendor invoice form with invoice "1000"
    Given The user login with "sahamuduru"
    Given The user go in Task and submit
    Given The user login with "offtradeuzmani"
    Given The user go in Task and submit
    Given The user verify blocked budget with "0"
    Given The user verify actual budget with "1000"

  Scenario: Plain Modul Made Flow With Reject sales
    Given The user get blocked budget
    Given The user get actual budget
    Given The user login with "sahamuduru"
    Given The user go to "panel" page
    Given The user go in "Modül Akışı" flow
    Given The user select "Modül Yapım" as form type
    Given The user fill start form with musteri code "999999999"
    Given The user verify blocked budget with "10000"
    Given The user login with "satismuduru"
    Given The user go in Task "DIA: ConfirmationForm"
    Given The user reject the task
    Given The user verify blocked budget with "0"

  Scenario: Plain Modul Made Flow With Revise sales
    Given The user get blocked budget
    Given The user get actual budget
    Given The user login with "sahamuduru"
    Given The user go to "panel" page
    Given The user go in "Modül Akışı" flow
    Given The user select "Modül Yapım" as form type
    Given The user fill start form with musteri code "999999999"
    Given The user verify blocked budget with "10000"
    Given The user login with "satismuduru"
    Given The user go in Task "DIA: ConfirmationForm"
    Given The user revise the task
    Given The user verify blocked budget with "10000"

#  Scenario: Plain Modul Made Flow With Reject offtrade
#    Given The user get blocked budget
#    Given The user get actual budget
#    Given The user login with "sahamuduru"
#    Given The user go to "panel" page
#    Given The user go in "Modül Akışı" flow
#    Given The user select "Modül Yapım" as form type
#    Given The user fill start form with musteri code "999999999"
#    Given The user verify blocked budget with "10000"
#    Given The user login with "satismuduru"
#    Given The user go in Task and submit
#    Given The user login with "offtrademanager"
#    Given The user go in Task "DIA: ConfirmationForm"
#    Given The user reject the task
#    Given The user verify blocked budget with "0"


#------------------------------------------
  Scenario: Plain Modul Budget Flow Without Reject or Revise
    Given The user get blocked budget
    Given The user get actual budget
    Given The user login with "sahamuduru"
    Given The user go to "panel" page
    Given The user go in "Modül Akışı" flow
    Given The user select "Modül Bütçe Desteği" as form type
    Given The user fill start form with musteri code "999999999" and budget "1250"
    Given The user verify blocked budget with "1250"
    Given The user login with "satismuduru"
    Given The user go in Task and submit
    Given The user login with "bolgemuduru"
    Given The user go in Task and submit
    Given The user login with "offtrademanager"
    Given The user go in Task "DIA: ConfirmationForm"
    Given The user change distributor help
    Given The user verify blocked budget with "0"
    Given The user change distributor help
    Given The user verify blocked budget with "1250"
    Given The user submit the task
    Given The user login with "sahamuduru"
    Given The user go in Task "DIA: SalesFlowForm"
    Given The user fill invoice form with amount "1100"
    Given The user login with "offtradeuzmani"
    Given The user go in Task and submit
    Given The user login with "offtrademanager"
    Given The user go in Task and submit
    Given The user verify blocked budget with "0"
    Given The user verify actual budget with "1100"


#  Scenario: Plain Modul Budget Flow Without Reject or Revise 2
#    Given The user get blocked budget
#    Given The user get actual budget
#    Given The user login with "sahamuduru"
#    Given The user go to "panel" page
#    Given The user go in "Modül Akışı" flow
#    Given The user select "Modül Bütçe Desteği" as form type
#    Given The user fill start form with musteri code "999999999" and budget "1250"
#    Given The user verify blocked budget with "1250"
#    Given The user login with "satismuduru"
#    Given The user go in Task and submit
#    Given The user login with "bolgemuduru"
#    Given The user go in Task and submit
#    Given The user login with "offtrademanager"
#    Given The user go in Task "DIA: ConfirmationForm"
#    Given The user change distributor help
#    Given The user verify blocked budget with "0"
#    Given The user change distributor help
#    Given The user verify blocked budget with "1250"
#    Given The user submit the task

  Scenario: Plain Modul Budget Flow With Reject
    Given The user get blocked budget
    Given The user get actual budget
    Given The user login with "sahamuduru"
    Given The user go to "panel" page
    Given The user go in "Modül Akışı" flow
    Given The user select "Modül Bütçe Desteği" as form type
    Given The user fill start form with musteri code "999999999" and budget "1250"
    Given The user verify blocked budget with "1250"
    Given The user login with "satismuduru"
    Given The user go in Task "DIA: ConfirmationForm"
    Given The user reject the task
    Given The user verify blocked budget with "0"













