Feature: Delete an item

Scenario: Delete an item that existing in the cart
Given User is on login Page and enters "Dharun_K" and "dkvk2315"
When User delete an item from the Cart Page
Then the item should be deleted from the Cart Page
