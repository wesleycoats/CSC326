#Author wrcoats

Feature: Document or edit information for a Childbirth Hospital Visit
	As a HCP
	I should be able to document or edit a Childbirth Hospital Visit
	For a specific patient who had a Childbirth Hospital visit scheduled at a routine office visit or emergency room visit
	
Scenario Outline: Giving birth to twins after an obstetrics office visit, vaginal delivery w/ pitocin and epidural anaesthesia
	Given I log in as Kelly Doctor
	When I search for Princess Peach by MID and select Princess Peach
	And Princess Peach has a childbirth visit scheduled during an obstetrics visit
	And I go to enter childbirth information and I enter <epiduralAnaesthesiaDosage>, <magnesiumSulfateDosage>, <nitrousOxideDosage>, <pethidineDosage>, <pitocinDosage>, <preferredDelivery>, <scheduled>
	And I submit the childbirth data
	And Princess Peach has fraternal twins Toad and Toadette at 2:30 on May 17 of the current year
	Then new patient file(s) is/are created for the baby(s)
	
Scenario Outline: Giving birth to a daughter after an emergency room visit, vaginal vaccuum assist w/ pethidine and nitrous oxide
	Given I log in as Kelly Doctor
	When I search for Daria Griffin by MID and select Daria Griffin
	And Daria Griffin is in labor and enters the emergency room
	And a childbirth hospital visit is scheduled
	And I go to enter childbirth information and I enter <epiduralAnaesthesiaDosage>, <magnesiumSulfateDosage>, <nitrousOxideDosage>, <pethidineDosage>, <pitocinDosage>, <preferredDelivery>, <scheduled>
	And I submit the childbirth data
	And Daria Griffin gives birth and I enter info for Carly Griffin
	Then new patient file(s) is/are created for the baby(s)	

Scenario Outline: Giving birth to a son while still in the emergency room
	Given I log in as Kelly Doctor
	When I search for Daria Griffin by MID and select Daria Griffin
	And Daria Griffin is in labor and enters the emergency room
	And a childbirth hospital visit is scheduled
	And Daria Griffins gives birth while in the emergency room
	Then vaginal delivery is set by default
	And her son Chuck Griffin is entered into the system with birthdate 15:00 June 1st 2017
	
Scenario Outline: Enter wrong name and MID before proceeding, vaginal delivery w/ pethidine and nitrous oxide
	Given I log in as Kelly Doctor
	When I search for Daryl Griffin instead of Daria Griffin
	And I search for MID 103 instead of MID 104
	And I search for Daria Griffin by MID and select Daria Griffin
	And Daria Griffin is in labor and enters the emergency room
	And a childbirth hospital visit is scheduled
	And I go to enter childbirth information and I enter <epiduralAnaesthesiaDosage>, <magnesiumSulfateDosage>, <nitrousOxideDosage>, <pethidineDosage>, <pitocinDosage>, <preferredDelivery>, <scheduled>
	And Daria Griffin gives birth and I enter info for Carly Griffin
	Then new patient file(s) is/are created for the baby(s)	
	
	  	