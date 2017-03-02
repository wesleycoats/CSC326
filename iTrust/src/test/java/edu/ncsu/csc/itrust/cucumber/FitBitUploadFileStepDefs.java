package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class FitBitUploadFileStepDefs {
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as HCP, 1")
	public void loginHCP1() throws Throwable{
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000000");
		pass.sendKeys("pw");
		pass.submit();
		assertEquals("iTrust - HCP Home", driver.getTitle());
	}
	
	@And("^Navigate to the FitBit Data Page")
	public void navtoFitBit() throws Throwable{
		driver.findElement(By.linkText("FitBit Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	}
	
	@And("^Selectz a Patient")
	public void selPat() throws Throwable{
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - FitBit Calendar", driver.getTitle());
	}

	@And("^Select Upload$")
	public void select_uploadTab() throws Throwable {
		driver.findElement(By.name("upload")).click();
	}
	
	@Then("^Redirected to Upload")
	public void redirectCheck() throws Throwable{
		assertEquals("iTrust - Upload File", driver.getTitle());
	}

}