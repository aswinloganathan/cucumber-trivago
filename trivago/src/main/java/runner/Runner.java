package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

public class Runner {
	
	@CucumberOptions(features = "src/main/java/feature/trivago.feature", glue = "Steps", monochrome = true)
	public class Runtest extends AbstractTestNGCucumberTests{
		
	}
}
