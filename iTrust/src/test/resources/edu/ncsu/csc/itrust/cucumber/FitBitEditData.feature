#Author rvisador
Feature: Edit/Add Data in FitBit Calendar
	As a HCP
	I should be able to edit or fill out FitBit data
	For a specific patient and a spcific date

Scenario: Edit FitBit Data
	Given I log in as an HCP, HCP 1
	And Navigate to the FitBit Data page
	When I select a valid patient
	And Select a Date on the calendar
	And Enter new data into the fields
	And Submit the new data page
	Then The data should be submitted correctly
	And Be consistent when accessed by other HCP's