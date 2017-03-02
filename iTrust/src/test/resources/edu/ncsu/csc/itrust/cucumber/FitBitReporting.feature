#Author pdsherk

Feature: Run a Report on FitBit Data
	As a HCP
	I should be able to run a variety of graphs/charts
	To easily display data trends
	
Scenario: Run Line Graph
	Given I log in as the 1st HCP
	And go to the FitBit data page
	When I input a Patient and click enter and confirm the page goes to the calendar landing page
	Then clicking on the Run Report button the Report page will open
	And inputting a start and end date
	Then it will stay on the page