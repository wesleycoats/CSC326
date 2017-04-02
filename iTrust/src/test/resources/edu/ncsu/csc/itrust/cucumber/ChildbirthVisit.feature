#Author wrcoats

Feature: Document or edit information for a Childbirth Hospital Visit
	As a HCP
	I should be able to document or edit a Childbirth Hospital Visit
	For a specific patient who had a Childbirth Hospital visit scheduled at a routine office visit or emergency room visit
	
Scenario Outline: Giving birth to twins after an obstetrics office visit
	Given I log in as Kelly Doctor
	And I search for Princess Peach by MID and select Princess Peach
	And Princess Peach has a childbirth visit scheduled during an obstetrics visit
	And I select vaginal delivery as my preferred childbirth method
	And I choose Pitocin and Epidural anesthesia as the drugs
	When Princess Peach has fraternal twins Toad and Toadette at 2:30 on May 17 of the current year
	Then new patient file(s) is/are created for the baby(s)
	
Scenario Outline: Giving birth to a daughter after an emergency room visit
	Given I log in as Kelly Doctor
	And I search for Daria Griffin by MID
	And Daria Griffin is in labor and enters the emergency room
	And a childbirth hospital visit is scheduled
	And I select vaginal delivery vaccuum assist as my preffered childbirth method
	And I choose Pethidine and Nitrous oxide as the drugs
	When Daria Griffin gives birth and I enter info for Carly Griffin
	Then new patient file(s) is/are created for the baby(s)	

Scenario Outline: Giving birth to a son while still in the emergency room
	Given I log in as Kelly Doctor
	And I search for Daria Griffin by MID
	And Daria Griffin is in labor and enters the emergency room
	And a childbirth hospital visit is scheduled
	When Daria Griffins gives birth while in the emergency room
	Then vaginal delivery is set by default
	Then her son Chuck Griffin is entered into the system with birthdate 15:00 June 1st 2017
	
Scenario Outline: Enter wrong name and MID before proceeding
	Given I log in as Kelly Doctor
	And I search for Daryl Griffin instead of Daria Griffin
	And I search for MID 103 instead of MID 104
	And I search for Daria Griffin by MID
	And Daria Griffin is in labor and enters the emergency room
	And a childbirth hospital visit is scheduled
	And I select vaginal delivery as my preffered childbirth method
	And I choose Pethidine and Nitrous oxide as the drugs
	When Daria Griffin gives birth and I enter info for Carly Griffin
	Then new patient file(s) is/are created for the baby(s)	
	
	  	