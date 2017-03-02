package edu.ncsu.csc.itrust.cucumber;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class ReadMessagesStepDefs {
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as HCP 1$")
	public void login_to_read_messages() throws Throwable{
		
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000000");
		pass.sendKeys("pw");
		pass.submit();

		
//		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
	}
	
	@And("^Navigate to the Message Inbox$")
	public void navigate_messages() throws Throwable {
		
		driver.findElement(By.linkText("Message Inbox")).click();
		assertEquals("iTrust - View My Message", driver.getTitle());
		
	}
	
	@And("^the first message is unread$")
	public void check_unread() throws Throwable {
				
		WebElement mailbox = driver.findElement(By.id("mailbox"));
		List<WebElement> messages = mailbox.findElements(By.tagName("tr"));
		String fontWeight = messages.get(1).getAttribute("style");
		assertTrue(fontWeight.equals("font-weight: bold;"));
		
	}
	
	@When("^I read the first message$")
	public void read_msg() throws Throwable {
		
		driver.findElement(By.xpath("//a[@href='viewMessageInbox.jsp?msg=0']")).click();
		assertEquals("http://localhost:8080/iTrust/auth/hcp-patient/viewMessageInbox.jsp?msg=0", driver.getCurrentUrl());

	}
	
	@And("^Return using the Return to message inbox link$")
	public void return_to_inbox() throws Throwable {
		
		try{
			driver.findElement(By.linkText("Return to message inbox")).click();
		} catch(NoSuchElementException e){
			Assert.fail();
		}
		assertEquals("iTrust - View My Message", driver.getTitle());
		
	}
	
	@Then("^The first message will not be bold$")
	public void check_bold() throws Throwable {
		
		driver.setJavascriptEnabled(true);
		WebElement mailbox = driver.findElement(By.id("mailbox"));
		List<WebElement> messages = mailbox.findElements(By.tagName("tr"));
		String fontWeight = messages.get(0).getCssValue("font-weight");
		assertEquals("null", fontWeight);


		
	}
	
	
}
