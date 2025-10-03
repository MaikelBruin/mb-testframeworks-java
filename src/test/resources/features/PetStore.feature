@all
Feature: Petstore scenarios
  Scenario: Get pets
    Given I have done some configuration
    When I get all pets by tag "dog"
    Then there should be at least 2 pets with this tag in the petstore