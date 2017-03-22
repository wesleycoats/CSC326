#Author wrcoats

Feature: Create a new record of Obstetrics info
	As a HCP
	I should be able to create a new Obstetrics record
	For a specific patient

#UC 93 [S1][S2][S3]
Scenario: Display obstetrics information
	Given I log in as HCP 3
	When I search for Person Random
	And select Person Random
	And select their first Obstetrics record 
	Then their obstetrics information is displayed

#UC 93	[S1][S2][S3][E3][E4]
Scenario: Enter invalid input for number of children in prior pregnancy
	Given I log in as HCP 3
	When I search for MID 97 New Patient
	And an error message displays since they are not eligible for obstetrics
	And I change New Patient to be eligible for obstetrics
	And enter 0 for number of children in a prior pregnancy
	Then an error message displays