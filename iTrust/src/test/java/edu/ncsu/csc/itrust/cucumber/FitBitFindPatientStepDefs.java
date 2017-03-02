package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.HttpUnitOptions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * A class to test the FitBit lookup patient functionality.
 * 
 * @author Peter D. Sherk
 *
 */
public class FitBitFindPatientStepDefs {
	
	/**Selenium WebDriver*/
	private HtmlUnitDriver driver = new HtmlUnitDriver();
	/**The base URl*/
	private String BASE_URL = "http://localhost:8080/iTrust/";
	
	@Given("^I log in as an HCP using (.+) and (.+)$")
	public void login_HCP(String username, String password) {
		driver.setJavascriptEnabled(false);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
		HttpUnitOptions.setExceptionsThrownOnScriptError(false);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(BASE_URL);
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys(username);
		pass.sendKeys(password);
		pass.submit();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
	}
	
	@And("^I navigate to the FitBit Patient locator page$")
	public void navigate_to_load_page() {
		driver.findElement(By.linkText("FitBit Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	}
	
	@And("^I select a valid Patient using the name or MID (.+) and click enter$")
	public void find_patient_by_name(String name) {
		driver.findElement(By.id("searchBox")).sendKeys(name);
		driver.findElement(By.name("UID_PATIENTID")).sendKeys(name);
		driver.findElement(By.xpath("//input[@value='"+name+"']")).submit();
		assertTrue(true);
	}
	
	@Then("^I should be viewing the landing page for Patient Fitbit Data$")
	public void fitbit_landing_page() {
		assertEquals("iTrust - FitBit Calendar", driver.getTitle());
	}

}
