#Author wrcoats
#		bmhogan

Feature: Document or edit information for an Obstetrics Visit
	As a HCP
	I should be able to document or edit an Obstetrics Visit
	For a specific patient who has been initialized as an obstetrics patient

#UC 94 [S1][S2][S3]	
Scenario Outline: Entering valid obstetrics visit information
	Given I log in as Gandalf Stormcrow
	And I search for Sporty Spice by MID and select Sporty Spice
	And I go to enter Obstetrics visit information
	And I enter <WeeksPreggo>, <Weight>, <BloodPressure>, <FHR>, <numChildren>, <Placenta>
	And I submit the data
	Then no error message is shown and the data is saved to the database
	
	Examples:
	| WeeksPreggo | Weight | BloodPressure | FHR | numChildren | Placenta |
	| 13          |  192.8 | 70 	       | 178 | 1           | False    |
	| 41          |  192.8 | 70 	       | 178 | 1           | False    |
	| 13          |  75.0  | 70 	       | 178 | 1           | False    |
	| 13          |  192.8 | 130 	       | 178 | 1           | False    |
	| 13          |  192.8 | 70 	       | 104 | 1           | False    |
	| 13          |  192.8 | 70 	       | 178 | 2           | False    |
	| 13          |  192.8 | 70 	       | 178 | 1           | True     |
	| 2           |  303.3 | 30 	       | 205 | 4           | True     |
	
Scenario Outline: Entering invalid obstetrics visit information
	Given I log in as Gandalf Stormcrow
	And I search for Sporty Spice by MID and select Sporty Spice
	And I go to enter Obstetrics visit information
	And I enter <WeeksPreggo>, <Weight>, <BloodPressure>, <FHR>, <numChildren>, <Placenta>
	And I submit the data
	Then an error message is shown and the data is not saved to the database
	
	Examples:
	| WeeksPreggo | Weight | BloodPressure | FHR | numChildren | Placenta |
	| 13          |  192.8 | 70 	       | 178 | 1           | null     |
	| -1          |  192.8 | 70 	       | 178 | 1           | False    |
	| 13          |  -1    | 70 	       | 178 | 1           | False    |
	| 13          |  192.8 | -1  	       | 178 | 1           | False    |
	| 13          |  192.8 | 70 	       | -1  | 1           | False    |
	| 13          |  192.8 | 70 	       | 178 | -1          | False    |
	
	
Scenario: Scheduling a future Childbirth appointments
	Given I log in as Gandalf Stormcrow
	And I search for Sporty Spice by MID and select Sporty Spice
	And Sporty Spice is 42 weeks pregnant
	Then the next appointment that is scheduled is a Childbirth Visit
	
	
Scenario: Scheduling a future Obstetrics appointment
	Given I log in as Gandalf Stormcrow
	And I search for Sporty Spice by MID and select Sporty Spice
	And Sporty Spice is 41 weeks pregnant
	Then the next appointment that is scheduled is another Obstetrics Visit
	
	
Scenario Outline: Dr. Seuss gives an ultrasound
	Given I log in as Gandalf Stormcrow
	And I search for Sporty Spice by MID and select Sporty Spice
	And I choose to give an ultrasound
	When I enter the following information: <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>
	And submit the ultrasound data
	Then no error message is shown and the data is saved to the database
	
	Examples:
	| CRL | BPD | HC  | FL | OFD | AC | HL  | EFW |
	| 3.4 | 2.2 | 1.1 | 2  | 2.2 | 4  | 3.3 | 2.3 |
	| 3.4 | 5.4 | 1.1 | 2  | 2.2 | 4  | 3.3 | 2.3 |
	| 3.4 | 2.2 | 0.3 | 4  | 6.2 | 4  | 3.3 | 7.3 |
	

Scenario Outline: Dr. Seuss records incorrect Ultrasound data
	Given I log in as Gandalf Stormcrow
	And I search for Sporty Spice by MID and select Sporty Spice
	And I choose to give an ultrasound
	When I enter the following information: <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>
	And submit the ultrasound data
	Then an error message is shown and the data is not saved to the database
	
	Examples:
	| CRL | BPD | HC  | FL | OFD | AC | HL  | EFW |
	| -3  | 2.2 | 1.1 | 2  | 2.2 | 4  | 3.3 | 2.3 |
	| 3.4 | -5  | 1.1 | 2  | 2.2 | 4  | 3.3 | 2.3 |
	| 3.4 | 2.2 | -1  | 4  | 6.2 | 4  | 3.3 | 7.3 |
	| 3.4 | 2.2 | 1.1 | -4 | 6.2 | 4  | 3.3 | 7.3 |
	| 3.4 | 2.2 | 1.1 | 4  | -2  | 4  | 3.3 | 7.3 |
	| 3.4 | 2.2 | 1.1 | 4  | 6.2 | -4 | 3.3 | 7.3 |
	| 3.4 | 2.2 | 1.1 | 4  | 6.2 | 4  | -3  | 7.3 |
	| 3.4 | 2.2 | -1  | 4  | 6.2 | 4  | 3.3 | 0   |


Scenario: Non OB/GYN requested to make regular office visit
	Given I log in as Kelly Doctor
	And I search for Sporty Spice by MID and select Sporty Spice
	And I go to enter Obstetrics visit information
	Then I am unable to create an Obstetrics visit and am prompted to make a general office visit