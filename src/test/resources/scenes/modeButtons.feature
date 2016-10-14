Feature: annotation

  Background:
  Given that the game is started
  And that the scene is ChooseMode

#Scenario with AND
  Scenario:
    When I press the menu-button
    Then the scene should be Menu

  Scenario:
    When I press the space-button
    Then the scene should be ChooseMode
    And the mode should be space

  Scenario:
    When I press the underwater-button
    Then the scene should be ChooseMode
    And the mode should be underwater

  Scenario:
    When I press the regular-button
    Then the scene should be ChooseMode
    And the mode should be regular

  Scenario:
      When I press the darkness-button
      Then the scene should be ChooseMode
      And the mode should be darkness

  Scenario:
      When I press the invert-button
      Then the scene should be ChooseMode
      And the mode should be invert

  Scenario:
      When I press the story-button
      Then the scene should be ChooseMode
      And the mode should be story