@all
@petstore
Feature: Petstore scenarios

  Scenario: Get pets
    Given I have done some configuration
    When I get all pets by tag "dog" using the petstore api
    Then there should be at least 2 pets with this tag in the petstore

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