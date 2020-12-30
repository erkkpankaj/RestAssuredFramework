package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "C:\\Automation\\APIFramework\\src\\test\\java\\featureFiles\\placeValidate.feature",
plugin = "json:target/jsonReports/cucumber-report.json", 
glue = {"stepDefinitions" })
public class TestRunner {

}
