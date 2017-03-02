#jtcasper
Feature: Reading a message correctly displays as unread when navigating back
	As an HCP
	The ability to navigate around the message inbox and have correctly displayed information
	Is important
	
Scenario: Check message from user
	Given I log in as HCP 1
	And Navigate to the Message Inbox
	And the first message is unread
	When I read the first message
	And Return using the Return to message inbox link
	Then The first message will not be bold	