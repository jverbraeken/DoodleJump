Feature: annotation

  Background:
  Given that the game is started
  And that the scene is Menu

#Scenario with AND
  Scenario:
    When I press the play-button
    Then the scene should be World
    And the score should be 0


  Scenario:
    When I press the mode-button
    Then the scene should be ChooseMode

  Scenario:
    When I press the scorescreen-button
    Then the scene should be ScoreScreen

  Scenario:
    When I press the multiplayer-button
    Then the scene should be World
    And the score should be 0
