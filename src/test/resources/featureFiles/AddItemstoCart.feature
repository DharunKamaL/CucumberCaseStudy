Feature: Add items to cart

Scenario: Add items to the Cart from the Home Page
Given User is on login Page and enters "Dharun_K" and "dkvk2315"
When User adds an items
 |	productCategory	|		 productName		|
 |			Phones			|	Samsung galaxy s6 |
 |			Phones			|	 Nokia lumia 1520 |
 |			Monitors		|	Apple monitor 24 	|	
 |			Monitors		|		ASUS Full HD 		|
Then the products are added to the Cart