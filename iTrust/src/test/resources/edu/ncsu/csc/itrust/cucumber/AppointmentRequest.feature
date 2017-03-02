#Author jtcasper
Feature: Create appointment Request
	As a user
	I should be able to schedule an appointment
	Requesting my default HCP

Scenario: Create appointment Request
	Given I log in as Patient 2 here
	And Navigate to the appointment request page
	When I input test values
	And Submit the page
	Then HCP 3 should have an appointment request that matches