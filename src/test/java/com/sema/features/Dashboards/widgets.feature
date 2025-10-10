@widget
Feature: Widget Scenarios
  Background:
    Given The user get data

  Scenario: Scenario 1
    Given The user send widget11 request
    Given The user send widget12 request
    Then The user verify actuals

  Scenario: Scenario 2
    Given The user send widget13 request 'Trakya'
    Given The user send widget14 request 'Trakya'
    Then The user verify scenario2

  Scenario: Scenario 3
    Given The user send widget15 request
    Given The user send widget11 request
    Then The user verify scenario3

  Scenario: Scenario 4
    Given The user send widget16 request
    Given The user send widget11 request
    Then The user verify scenario4

  Scenario: Scenario 5
    Given The user send widget17 request
    Given The user send widget12 request
    Then The user verify scenario5

  Scenario: Scenario 6
    Given The user send widget18 request
    Given The user send widget17 request
    Then The user verify scenario6

  Scenario: Scenario 7
    Given The user send widget19 request
    Given The user send widget17 request
    Then The user verify scenario7

#  Scenario: Scenario 8
#    Given The user send widget20 request
#    Given The user send widget19 request
#    Then The user verify scenario8
#
#  Scenario: Scenario 9
#    Given The user send widget21 request
#    Given The user send widget19 request
#    Then The user verify scenario9
#
#  Scenario: Scenario 10
#    Given The user send widget23 request
#    Given The user send widget21 request
#    Then The user verify scenario10

  Scenario: Scenario 11
    Given The user send widget24 request
    Given The user send widget23 request
    Then The user verify scenario11

  Scenario: Scenario 12
    Given The user send widget24 request
    Given The user send widget12 request
    Then The user verify scenario12

  Scenario: Scenario 13
    Given The user send widget25 request
    Given The user send widget47 request
    Then The user verify scenario13

  Scenario: Scenario 14
    Given The user send widget25 request
    Given The user send widget101 request
    Then The user verify scenario14

  Scenario: Scenario 15
    Given The user send widget26 request
    Given The user get Scenario15 query
    Then The user verify scenario15

  Scenario: Scenario 16
    Given The user send widget26 request
    Given The user send widget27 request
    Then The user verify scenario16

  Scenario: Scenario 17
    Given The user send widget28 request
    Given The user send widget29 request
    Then The user verify scenario17

  Scenario: Scenario 18
    Given The user send widget28 request
    Given The user send widget30 request
    Then The user verify scenario18

  Scenario: Scenario 19
    Given The user send widget38 request
    Given The user send widget43 request
    Then The user verify scenario19

  Scenario: Scenario 20
    Given The user send widget94 request
    Given The user send widget95 request
    Then The user verify scenario20

  Scenario: Scenario 22
    Given The user send widget96 request
    Given The user send widget97 request
    Then The user verify scenario22

  Scenario: Scenario 23
    Given The user send widget98 request
    Given The user send widget43 request
    Then The user verify scenario23

  Scenario: Scenario 24
    Given The user send widget99 request
    Given The user send widget43 request
    Then The user verify scenario24

  Scenario: Scenario 26
    Given The user send widget1 request
    Given The user get S26 query
    Then The user verify scenario26

  Scenario: Scenario 27
    Given The user send widget2 request
    Given The user get S27 query
    Then The user verify scenario27

  Scenario: Scenario 28
    Given The user send widget3 request
    Given The user get S28 query
    Then The user verify scenario28

  Scenario: Scenario 29
    Given The user send widget4 request
    Given The user send widget8 request
    Then The user verify scenario29

  Scenario: Scenario 30
    Given The user send widget9 request
    Given The user get S30 query
    Then The user verify scenario30

  Scenario: Scenario 31
    Given The user send widget10 request
    Given The user get S31 query
    Then The user verify scenario31

  Scenario: Scenario 32
    Given The user send widget22 request
    Given The user send widget15Aggregation request
    Then The user verify scenario32

  Scenario: Scenario 33
    Given The user send widget31 request
    Given The user send widget12Aggreation request
    Then The user verify scenario33

  Scenario: Scenario 34
    Given The user send widget100 request
    Given The user send widget25Aggreation request
    Then The user verify scenario34

  Scenario: Scenario 39
    Given The user send widget49 request
    Given The user send widget44 request
    Given The user get S39 query
    Then The user verify scenario39

  Scenario: Scenario 40
    Given The user send widget51 request
    Given The user send widget13 request 'Trakya'
    Then The user verify scenario40

  Scenario: Scenario 41
    Given The user send widget52 request
    Given The user send widget44 request
    Then The user verify scenario41

  Scenario: Scenario 42
    Given The user send widget52 request
    Given The user send widget45 request
    Then The user verify scenario42

  Scenario: Scenario 47
    Given The user send widget53 request
    Given The user send widget48 request
    Then The user verify scenario47

  Scenario: Scenario 48
    Given The user send widget54 request
    Given The user send widget48AggreationS48 request
    Then The user verify scenario48

  Scenario: Scenario 49
    Given The user send widget55 request
    Given The user send widget48AggreationS49 request
    Then The user verify scenario49

#  Scenario: Scenario 50
#    Given The user send widget56 request
#    Given The user send widget48AggreationS50 request
#    Then The user verify scenario50

  Scenario: Scenario 51
    Given The user send widget33 request
    Given The user send widget34 request
    Then The user verify scenario51

  Scenario: Scenario 52
    Given The user send widget33 request
    Given The user send widget35 request
    Then The user verify scenario52

  Scenario: Scenario 61
    Given The user send widget87 request
    Given The user send widget84 request
    Then The user verify scenario61

  Scenario: Scenario 62
    Given The user send widget89 request
    Given The user send widget84 request
    Then The user verify scenario62

  Scenario: Scenario 63
    Given The user send widget90 request
    Given The user send widget84 request
    Then The user verify scenario63

  Scenario: Scenario 64
    Given The user send widget91 request
    Given The user send widget84 request
    Then The user verify scenario64
