#Author rvisador
Feature: Edit Personnel Demographics
	As staff
	I should be able to change my Demographic information

Scenario: Edit Personnel Demographics
	Given I log in as Admin
	And Navigate to the Edit My Demographics page
	When I edit my first name
	And Submit personnel page
	Then Admin should have the new changed name