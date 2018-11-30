# Restructured-Tables

This repository will provide an implementation to the following data model and provide an UI for interacting with it.

## Scouting Tables Model Standard

##### A. Goals of the Model

1. This model aims to provide a standard to store, communicate, analyze, version, and archive FRC scouting data. The intended end user of this model are strategists, data analysists, and decision making groups of an FRC team that require organized scouting data
2. This model also aims to provide a standard of communication between internal applications

##### B. General Definitions (Details to Follow)

1. An *Application* is a program that implements the Model
2. A *Working Directory* contains files and records organized according to the Model
3. An *Instance* of the Model is an Application using a specific Working Directory
4. An *User* is a person using an Instance and is referenced in the form of `First Last`
5. A *Table* is a two dimensional data structure with unique row and column headers, and will always have the same data structure in each column
6. A *Table Type* is the representation type of a Table's row headers
7. *Event*, *Match*, *Team*, *Entry*, *Scout* are specific Table Types used for FRC Scouting Data
8. A *Table Set* is a set of Tables with unique Table Types
9. The *Data* is the record of a Table Set used by an Instance for information analysis
10. *Configurations* represent any state of the Working Directory that is not the Data and that represent some arrangements affecting the state of the Instance
11. A *View* is a Configuration that structures and displays the Data in a specific way without modifying it

#####  C. Model Instance Configuration

1. Each instance of the model should record one user of that instance.
2. For the purposes of attributing changes and actions to the user who made them, all such changes and actions should have the ability to record their user.
3. All data meant to communicate between instances of the model must contain its source user

