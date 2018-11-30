# Restructured-Tables

This repository will provide an implementation to the following data model and provide an UI for interacting with it.

## Scouting Tables Model Standard

##### A. Goals of the Model

1. This model aims to provide a standard to store, communicate, analyze, version, and archive FRC scouting data. The intended end user of this model are strategists, data analysists, and decision making groups of an FRC team that require organized scouting data.
2. This model also aims to provide a standard of communication between internal applications

##### B. User Attribution

1. For the purposes of attributing changes and actions to the user who made them, all such changes and actions should have the ability to record their user.
2. User data should consist of the user's name in the form of `First Last`
3. All data meant to communicate between instances of the model must contain its source user

##### C. Model Instance Configuration

1. Each instance of the model should record one user of that instance.