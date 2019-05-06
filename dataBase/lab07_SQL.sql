--
-- File generated with SQLiteStudio v3.2.1 on sob. kwi 27 23:46:28 2019
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: administrator
CREATE TABLE administrator (login TEXT NOT NULL UNIQUE, password TEXT NOT NULL);

-- Table: Doctor
CREATE TABLE Doctor (doctorID INTEGER PRIMARY KEY UNIQUE NOT NULL, Name STRING NOT NULL, Surname STRING NOT NULL, roomNR INTEGER, username TEXT NOT NULL, password TEXT NOT NULL);
INSERT INTO Doctor (doctorID, Name, Surname, roomNR, username, password) VALUES (232, 'Hellen', 'Smith', 2, 'hSmith', 'smith');

-- Table: Patient
CREATE TABLE Patient (patientID INTEGER PRIMARY KEY UNIQUE NOT NULL, Name STRING NOT NULL, Surname STRING NOT NULL);
INSERT INTO Patient (patientID, Name, Surname) VALUES (1, 'Adam', 'Smith');

-- Table: patientHistory
CREATE TABLE patientHistory (patientID INTEGER REFERENCES Patient (patientID) NOT NULL, visitID INTEGER REFERENCES patientVisit NOT NULL);

-- Table: patientVisit
CREATE TABLE patientVisit (patientID INTEGER REFERENCES Patients (patientID) NOT NULL, appointment DATE NOT NULL, status BOOLEAN NOT NULL, resume TEXT, doctorID INTEGER REFERENCES Doctors (doctorID) NOT NULL, visitID INTEGER NOT NULL PRIMARY KEY);

-- Table: Schedule
CREATE TABLE Schedule (doctorID INTEGER REFERENCES Doctors (doctorID) NOT NULL, Date DATE NOT NULL, timeBegin DATETIME NOT NULL, timeEnd DATETIME NOT NULL);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
