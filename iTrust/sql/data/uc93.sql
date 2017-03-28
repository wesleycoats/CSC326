DROP TABLE IF EXISTS obstetrics;

CREATE TABLE obstetrics
(
    patientMID BIGINT(20) UNSIGNED NOT NULL,
    dateCreated DATETIME,
    lmp DATETIME NOT NULL,
    edd DATETIME NOT NULL,
    PRIMARY KEY (patientMID, dateCreated)
    
) ENGINE=MyISAM;

INSERT INTO obstetrics (
	patientMID, 
	dateCreated,
	lmp, 
	edd) 
VALUES (101, "2017-03-13 00:00:00", "2017-02-13 00:00:00", "2015-11-13 00:00:00");

INSERT INTO obstetrics (
	patientMID, 
	dateCreated,
	lmp, 
	edd) 
VALUES (100, "2017-03-15 00:00:00", "2017-02-15 00:00:00", "2015-11-15 00:00:00");
