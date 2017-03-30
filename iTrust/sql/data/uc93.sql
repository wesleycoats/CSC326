DROP TABLE IF EXISTS obstetrics;
DROP TABLE IF EXISTS pregnancies;

CREATE TABLE obstetrics
(
    patientMID BIGINT(20) UNSIGNED NOT NULL,
    dateCreated DATETIME,
    lmp DATETIME NOT NULL,
    edd DATETIME NOT NULL,
    PRIMARY KEY (patientMID, dateCreated)
    
) ENGINE=MyISAM;

CREATE TABLE pregnancies
(
	patientMID BIGINT(20) UNSIGNED NOT NULL,
	yearOfConception INT(10) UNSIGNED,
	weeksPregnant INT(10) UNSIGNED,
	hoursInLabor FLOAT UNSIGNED,
	weightGain FLOAT,
	deliveryType enum("vaginal delivery", "vaginal delivery vacuum assist", "vaginal delivery forceps assist", "caesarean section", "miscarriage"),
	numChildren INT(3),
	PRIMARY KEY (patientMID, yearOfConception),
	FOREIGN KEY (patientMID) REFERENCES patients(MID)
) ENGINE=MyISAM;

INSERT INTO obstetrics (
	patientMID, 
	dateCreated,
	lmp, 
	edd) 
VALUES (1, "2017-03-13 00:00:00", "2017-02-13 00:00:00", "2015-11-13 00:00:00");

INSERT INTO obstetrics (
	patientMID, 
	dateCreated,
	lmp, 
	edd) 
VALUES (0, "2017-03-15 00:00:00", "2017-02-15 00:00:00", "2015-11-15 00:00:00");

INSERT INTO pregnancies (
	patientMID,
	yearOfConception,
	weeksPregnant,
	hoursInLabor,
	weightGain,
	deliveryType,
	numChildren)
VALUES (0, 2017, 8, 0, 12.5, null, 1);
