Feature: pausing

  Background:
  Given that the game is started
  And that the scene is Menu
  And I press the play-button

#Scenario with AND
  Scenario:
    When the world is running
    And I pause the game
    Then the scene should be PauseScreen

  Scenario:
   When the world is paused
   And I resume the game
   Then the scene should be World

  Scenario:
    When the world is running
    And I do nothing
    Then the scene should be World

  Scenario:
   When the world is paused
   And I do nothing
   Then the scene should be PauseScreen