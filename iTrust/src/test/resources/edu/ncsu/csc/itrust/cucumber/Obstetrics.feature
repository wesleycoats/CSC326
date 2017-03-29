#Author wrcoats

Feature: Create a new record of Obstetrics info
	As a HCP
	I should be able to create a new Obstetrics record
	For a specific patient

#UC 93 [S1][S2][S3]
Scenario: Display obstetrics information
	Given I log in as HCP 3
	And I navigate to the Obstetrics Visit Patient locator page
	When I search for Person Random
	And select Person Random
	Then their lack of obstetrics visit information is displayed

#UC 93	[S1][S2][S3][E3][E4]
Scenario: Enter invalid input for number of children in prior pregnancy
	Given I log in as HCP 3
	And I navigate to the Obstetrics Visit Patient locator page
	When I search for Person Random
	And select Person Random
	And enter -1 for number of children in a prior pregnancy
	Then an error message displays