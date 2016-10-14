Feature: annotation

  Background:
  Given that the game is started
  And that the scene is ScoreScreen

#Scenario with AND

  Scenario:
      When I do nothing
      Then the scene should be ScoreScreen