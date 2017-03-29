DROP TABLE IF EXISTS obstetricsVisit;
DROP TABLE IF EXISTS officeVisit;

CREATE TABLE officeVisit
(
	visitID BIGINT(20) UNSIGNED NOT NULL,
	patientMID BIGINT(20) UNSIGNED NOT NULL,
	visitDate DATETIME NOT NULL,
	PRIMARY KEY (visitID),
	FOREIGN KEY (patientMID) REFERENCES patients(MID)
)  ENGINE=MyISAM;

CREATE TABLE obstetricsVisit
(
	visitID BIGINT(20) UNSIGNED,
	patientMID BIGINT(20) UNSIGNED NOT NULL,
	weeksPregnant INT(10) UNSIGNED,
	weight FLOAT,
	bloodPressure INT(3),
	fetalHeartRate INT(3),
	pregnancies INT(2),
	placentaObserved BOOLEAN,
	PRIMARY KEY (visitID),
	FOREIGN KEY(visitID) REFERENCES officeVisit(visitID),
	FOREIGN KEY (patientMID) REFERENCES patients(MID)
) ENGINE=MyISAM;

INSERT INTO obstetricsVisit (
	visitID,
	patientMID,
	weeksPregnant,
	weight,
	bloodPressure,
	fetalHeartRate,
	pregnancies,
	placentaObserved)
VALUES (1, 1, 3, 156.3, 85, 100, 1, TRUE);

INSERT INTO officeVisit (
	visitID,
	patientMID,
	visitDate)
VALUES (1, 1, "2017-03-15");

INSERT INTO obstetricsVisit (
	visitID,
	patientMID,
	weeksPregnant,
	weight,
	bloodPressure,
	fetalHeartRate,
	pregnancies,
	placentaObserved)
VALUES (2, 0, 7, 176.3, 75, 107, 7, FALSE);

INSERT INTO officeVisit (
	visitID,
	patientMID,
	visitDate)
VALUES (2, 0, "2017-03-16");

INSERT INTO officeVisit (
	visitID,
	patientMID,
	visitDate)
VALUES (3, 0, "2017-03-14");