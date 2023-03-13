Feature: Purchasing an item

Scenario: Place order
Given User is on login Page and enters "Dharun_K" and "dkvk2315"
When User places an order for the items
Then It should be ordered and purchased
