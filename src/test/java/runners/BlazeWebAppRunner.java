package runners;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src//test//resources//featureFiles",
		glue = {"stepDefs"},
		monochrome = true,
		plugin = {"pretty",
				"html:target//Reports//BlazeWebAppReport.html"},
		dryRun = false
		
		

		)


public class BlazeWebAppRunner extends AbstractTestNGCucumberTests {
  
}
