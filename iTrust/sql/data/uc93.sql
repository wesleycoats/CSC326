DROP TABLE IF EXISTS obstetricsVisit;

CREATE TABLE obstetricsVisit
(
	visitID BIGINT(20) UNSIGNED,
	patientMID BIGINT(20) UNSIGNED NOT NULL,
	lmp DATETIME NOT NULL,
	edd DATETIME NOT NULL,
	weeksPregnant INT(10) UNSIGNED,
	FOREIGN KEY(visitID) REFERENCES officeVisit(visitID),
	FOREIGN KEY (patientMID) REFERENCES patients(MID)
) ENGINE=MyISAM;

INSERT INTO obstetricsVisit (
	visitID,
	patientMID, 
	lmp, 
	edd, 
	weeksPregnant) 
VALUES (26, 101, "2015-11-13 00:00:00", "2015-11-13 00:00:00", 1);

INSERT INTO obstetricsVisit (
	visitID,
	patientMID, 
	lmp, 
	edd, 
	weeksPregnant) 
VALUES (1, 100, "2015-12-13 00:00:00", "2015-12-13 00:00:00", 3);
