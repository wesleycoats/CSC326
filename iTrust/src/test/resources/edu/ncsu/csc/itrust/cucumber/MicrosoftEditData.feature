#Author rvisador
Feature: Edit/Add Data in Microsft Data
	As a HCP
	I should be able to edit or fill out Microsoft Band data
	For a specific patient and a spcific date

Scenario: Edit Microsoft Data
	Given I have logged in as an HCP, HCP 1
	And Navigate to the Microsoft Data page
	When I will select a valid patient
	And Select a valid Date from the calendar
	And Enter data into the Microsoft Band fields
	And Submit the new data to the microsoft table
	Then A message will tell that the data is submitted correctly
	And Other HPC's will see the same data for the patient