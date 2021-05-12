Feature: AirQuality from City Search
  To allow a client to find the Air Quality from Portugal Cities.

  Scenario: Get Air Quality from City
    When I navigate to "http://localhost:8080/"
    And I select "Aveiro"
    And I click in  "Get Air Quality"
    Then the page title should start with "Aveiro"
