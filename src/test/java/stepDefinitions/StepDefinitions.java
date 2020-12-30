package stepDefinitions;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils {
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	RequestSpecification req;
	TestDataBuild data = new TestDataBuild();
	JsonPath js;
	static String place_id;
	
	// @Given("^Add place payload with \"([^\"]*)\" and \"([^\"]*)\" and
	// \"([^\"]*)\"$")
	@Given("Add place payload with {string} <{string}> <{string}>")
	public void add_place_paylod_with(String name, String language, String address) throws IOException {

		// Response builder
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		// Passing the request builder here, and getting the response (which is
		// converted in RequestSpecification)
		res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	@When("^User calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_with_post_http_request(String resource, String httpMethod) {

		// Constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		if (httpMethod.equalsIgnoreCase("POST"))
			// Getting the response by using res
			response = res.when().post(resourceAPI.getResource());
		else if (httpMethod.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("^The API call got success with status code \"([^\"]*)\"$")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}

	@And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void in_response_body_is(String keyValue, String expectedValue) {
		assertEquals(getJsonPath(response, keyValue), expectedValue);
	}

	@Then("verify place_id created maps to {string} using {string}")
	public void vefiry_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_post_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}
	
		@Given("Delete place playlaod")
		public void delete_place_playlaod() throws IOException {
			res=given().spec(requestSpecification()).body(data.deletePlacePayLoad(place_id));
		}

}
