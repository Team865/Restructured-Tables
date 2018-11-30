# Restructured-Tables

This repository will provide an implementation to the following data model and provide an UI for interacting with it.

## Scouting Tables Model

##### A. Goals of the Model

1. This model aims to provide a standard to store, communicate, analyze, version, and archive FRC Scouting Data. The intended end user of this model are strategists, data analysists, and decision making groups of an FRC team that require organized scouting data
2. This model also aims to provide a decentralized standard of communication between users of the model, which accounts for the use of multiple computers

##### B. General Definitions (Details to Follow)

1. An *Application* is a program that provides some interface to interact with the Model 
2. A *Working Directory* contains files and records organized according to the Model
3. An *Instance* of the Model is an Application using a specific Working Directory
4. An *User* is a person using an Instance
5. A *Table* is a two dimensional data structure with unique row and column headers, and will always have the same data structure in each column
6. A *Table Type* is the representation type of a Table's row headers
7. *Event*, *Match*, *Team*, *Entry*, *Scout* are specific Table Types used for FRC Scouting Data
8. A *Table Set* is a set of Tables with unique Table Types
9. The *Data* is a record of a Table Set in the Working Directory used by an Instance for information analysis
10. *Configurations* represent any state of the Working Directory that is not the Data and that represent some arrangements affecting the state of the Instance
11. A *Version* represents the difference in Data and Configurations of an Instance in a certain period of time, that is attributed to an User
12. A *View* is a Configuration that structures and displays the Data in a specific way without modifying it
13. A *Script* is a Configuration written in a programming language that aids the Instance in data analysis

#####  C. Working Directory Configuration

```
{ApplicationExecutabe}
{ScriptingExecutables...}/
{WorkingDirectory}/
	Backup/
	
```

1. The above structure specifies the basic Configuration of the Working Directory
2. For the purposes of attributing changes and actions to the user who made them, all such changes and actions should have the ability to record their user.
3. All data meant to communicate between instances of the model must contain its source user

##### D. Decentralization and Versioning

1. An Instance has the ability to update its Data and Configurations when given the Working Directory from another Instance. This means Data and Configurations can be transferred between devices
2. An Instance has the ability to merge Versions when updating from another Working Directory

