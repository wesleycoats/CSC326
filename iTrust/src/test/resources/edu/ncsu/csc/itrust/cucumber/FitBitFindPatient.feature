#Author pdsherk

Feature: Load FitBit Data for a Patient
	As a HCP
	I should be able to load Fitbit data for a Patient
	So I can edit and use the data
	
Scenario Outline: Load FitBit data using Patient name
	Given I log in as an HCP using <userName> and <password>
	And I navigate to the FitBit Patient locator page
	And I select a valid Patient using the name or MID <patient> and click enter
    Then I should be viewing the landing page for Patient Fitbit Data
    
Examples:
|userName   |password    |patient    	 |
|9000000000 |pw 		 |2      |
|9000000003 |pw 		 |2      |
|9000000007 |pw 		 |2      |
|9000000000 |pw 		 |5      |
|9000000003 |pw 		 |5      |
|9000000007 |pw 		 |5      |