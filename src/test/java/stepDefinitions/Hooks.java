package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlaceAPI")
	public void beforeScenario() throws IOException {
		// Write a code to get the place id
		// Execute this code when place id is NULL

		StepDefinitions sdef = new StepDefinitions();

		if (StepDefinitions.place_id == null) {
			sdef.add_place_paylod_with("KK", "Marathi", "Pune");
			sdef.user_calls_with_post_http_request("AddPlaceAPI", "POST");
			sdef.vefiry_place_id_created_maps_to_using("KK", "getPlaceAPI");
		}
	}
}
