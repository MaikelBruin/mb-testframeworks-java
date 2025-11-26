@all
@petstore
Feature: Petstore scenarios

  Scenario: Delete all pets
    Given I have done some configuration
    And I get all pets with status "available" using the petstore api
    When I deleted all pets from the pet store using the petstore api

  Scenario: Get pets
    Given I have done some configuration
    When I get all pets by tag "dog" using the petstore api
    Then the petstore get pets by tag response should not be null

  @petstore-analyzer
  Scenario Outline: Petstore analyzer should correctly count number of pets labeled dog
    Given I get all pets by tag "<tag>" using the petstore api
    When I get the total number of pets tagged "<tag>" using the petstore analyzer api
    Then the total number of tagged pets from the analyzer api should be equal to the total of the petstore api

    Examples: tags
      | tag |
      | dog |
      | cat |

  @petstore-analyzer
  Scenario: Petstore analyzer should correctly count number of available pets
    Given I get all pets with status "available" using the petstore api
    When I get the total number of available pets using the petstore analyzer api
    Then the total number of available pets from the analyzer api should be equal to the total from the petstore api

  @petstore-analyzer
  Scenario: hasAvailableRats should return true if there are rats
    Given I added a pet rat to the pet store using the petstore api
    When I check if there are any rats available using the petstore analyzer api
    Then the petstore analyzer should return "true"

  @petstore-analyzer
  Scenario: hasAvailableRats should return false if there aren't any rats
    Given I deleted all pet rats from the pet store using the petstore api
    When I check if there are any rats available using the petstore analyzer api
    Then the petstore analyzer should return "false"