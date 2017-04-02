#Author bmhogan

Feature: Document or edit information for an Obstetrics Visit
	As a HCP
	I should be able to document or edit an Obstetrics Visit
	For a specific patient who has been initialized as an obstetrics patient

#UC 94 [S1][S2][S3]	
Scenario Outline: Entering valid obstetrics visit information
	Given Gandalf Stormcrow is an HCP with MID: 9000000003 and role of OB/GYN
	When I log in as Gandalf Stormcrow
	And I search for Person Random by MID and select Person Random
	And I go to enter Obstetrics visit information and I enter <WeeksPreggo>, <Weight>, <BloodPressure>, <FHR>, <numChildren>, <Placenta>
	And I submit the data
	Then no error message is shown and the obstetrics data is saved to the database
	
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
	Given Gandalf Stormcrow is an HCP with MID: 9000000003 and role of OB/GYN
	When I log in as Gandalf Stormcrow
	And I search for Person Random by MID and select Person Random
	And I go to enter invalid Obstetrics visit information and I enter <WeeksPreggo>, <Weight>, <BloodPressure>, <FHR>, <numChildren>, <Placenta>
	And I submit the data
	Then an error message is shown and the obstetrics data is not saved to the database
	
	Examples:
	| WeeksPreggo | Weight | BloodPressure | FHR | numChildren | Placenta |
	| -1          |  192.8 | 70 	       | 178 | 1           | False    |
	| 13          |  -1    | 70 	       | 178 | 1           | False    |
	| 13          |  192.8 | -1  	       | 178 | 1           | False    |
	| 13          |  192.8 | 70 	       | -1  | 1           | False    |
	| 13          |  192.8 | 70 	       | 178 | -1          | False    |
	
	
Scenario Outline: Dr. Seuss gives an ultrasound
	Given Gandalf Stormcrow is an HCP with MID: 9000000003 and role of OB/GYN
	When I log in as Gandalf Stormcrow
	And I search for Person Random by MID and select Person Random
	And I choose to give an ultrasound and I enter the following information: <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>
	And submit the ultrasound data
	Then no error message is shown and the ultrasound data is saved to the database
	
	Examples:
	| CRL | BPD | HC  | FL | OFD | AC | HL  | EFW |
	| 3.4 | 2.2 | 1.1 | 2  | 2.2 | 4  | 3.3 | 2.3 |
	| 3.4 | 5.4 | 1.1 | 2  | 2.2 | 4  | 3.3 | 2.3 |
	| 3.4 | 2.2 | 0.3 | 4  | 6.2 | 4  | 3.3 | 7.3 |
	

Scenario Outline: Dr. Seuss records incorrect Ultrasound data
	Given Gandalf Stormcrow is an HCP with MID: 9000000003 and role of OB/GYN
	When I log in as Gandalf Stormcrow
	And I search for Person Random by MID and select Person Random
	And I choose to give an ultrasound and I enter the following invalid information: <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>
	And submit the ultrasound data
	Then an error message is shown and the ultrasound data is not saved to the database
	
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