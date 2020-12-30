Feature: Validating Place APIs 

@AddPlaceAPI
Scenario Outline: Validate Addplace API 

	Given Add place payload with "<name>" <"language"> <"address"> 
	When User calls "AddPlaceAPI" with "POST" http request 
	Then The API call got success with status code "200" 
	And "status" in response body is "OK" 
	And verify place_id created maps to "<name>" using "getPlaceAPI" 
	
	Examples: 
		|name|language|address|
		|KKPankaj|Hindi|Windsor Park|

@DeletePlaceAPI		
Scenario: Validate that Delete Place API 

	Given Delete place playlaod 
	When User calls "deletePlaceAPI" with "POST" http request 
	Then The API call got success with status code "200" 
	And "status" in response body is "OK"