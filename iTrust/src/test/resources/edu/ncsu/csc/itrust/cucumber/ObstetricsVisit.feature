#Author wrcoats

Feature: Document or edit information for an Obstetrics Visit
	As a HCP
	I should be able to document or edit an Obstetrics Visit
	For a specific patient who has been initialized as an obstetrics patient

#UC 94 [S1][S2][S3]	
Scenario: Entering and editing obstetrics visit information
	Given I log in as Dr Seuss
	Given Dr Sesuss is specialized for OB/GYN
	And I search for Sporty Spice by MID
	And I enter Obstetrics visit information for Sporty Spice
	
Scenario: Scheduling future appointments
	Given I log in as Dr Seuss
	Given Dr Seuss is specialized for OB/GYN
	And I search for Sporty Spice by MID
	And I say this is Sporty Spice second pregnancy in 49 weeks
	Then the next appointment is scheduled for Sporty Spice
	