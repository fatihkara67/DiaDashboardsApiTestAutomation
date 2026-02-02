@widget2
Feature: Widget2 Scenarios
  Background:
    Given The user get data

#12,29,31,33,35,38,39

#31

  Scenario: Scenario 14
    Given The user send widget25 request
    Given The user send widget101 request
    Then The user verify scenario14

  Scenario: Scenario 25
    Given The user send widget29 request
    Given The user send widget29AggreationS25 request
    Then The user verify scenario25

  Scenario: Scenario 31
    Given The user send widget10 request
    Given The user get S31 query
    Then The user verify scenario31

  Scenario: Scenario 32
    Given The user send widget22 request
    Given The user send widget15Aggregation request
    Then The user verify scenario32

  Scenario: Scenario 37
    Given The user send widget46 request
    Given The user get S37 query
    Then The user verify scenario37

  Scenario: Scenario 53
    Given The user send widget36 request
    Given The user send widget29AggreationS53 request
    Then The user verify scenario53