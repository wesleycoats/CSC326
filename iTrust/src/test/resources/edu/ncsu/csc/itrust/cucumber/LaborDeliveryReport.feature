#Author Brian Hogan

Feature: Document the Labor Delivery Report for a patient

Scenario: View Labor Delivery Report for a patient
	Given I login as Gandalf Stormcrow
	And I enter 1 past pregnancies for Person Random
	And I enter 1 past childbirth visit for Person Random
	And I enter 2 past Obstetrics visits for Person Random
	And I decide that Person Random has no STDs
	And I decide that Person Random was diagnosed with Cancer
	And god wanted Person Random was allergic to Penicillin
	And Person Random acquire Herpes Viral Infection of the Penis
	When I go to view the delivery report
	Then all of this information is correctly displayed