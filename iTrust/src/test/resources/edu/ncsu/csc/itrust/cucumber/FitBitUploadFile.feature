#author tjwhite5
Feature: Upload FitBit Data File
	As a HCP
	I should be able to upload a .csv file
	For a specific patient
	
Scenario: Upload .csv file
	Given I log in as HCP, 1
	And Navigate to the FitBit Data Page
	And Selectz a Patient
	And Select Upload
	Then Redirected to Upload