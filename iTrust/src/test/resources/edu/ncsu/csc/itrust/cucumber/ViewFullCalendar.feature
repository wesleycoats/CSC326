#Author jtcasper
Feature: View Full Calendar
	As a user
	I should be able to view my calendar

Scenario: View Full Calendar
	Given I log in as Patient 2
	When Navigate to the calendar page
	Then It loads successfully