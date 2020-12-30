package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

			// Request builder
			req = new RequestSpecBuilder().setBaseUri(getGlobalProperties("baseURL")).addQueryParam("key", "qaclick123")
					// Adding request logging filter for Request logs
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					// Adding response logging filter for Response logs
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}

	// Getting the base URL from global properties file
	public static String getGlobalProperties(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"C:\\Automation\\APIFramework\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}

	public String getJsonPath(Response response, String key) {
		String jsonresp = response.asString();
		JsonPath js = new JsonPath(jsonresp);
		return js.get(key).toString();
	}
}
