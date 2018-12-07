# Restructured-Tables

This repository will provide an implementation to the following data model and provide an UI for interacting with it.

## Scouting Tables Model

#### A. Goals of the Model

1. This model aims to provide a standard to store, communicate, analyze, version, and archive FRC Scouting Data. The intended end user of this model are strategists, data analysists, and decision making groups of an FRC team that require organized scouting data
2. This model also aims to provide a decentralized standard of communication between users of the model, which accounts for the use of multiple computers

#### B. General Definitions (Details to Follow)

1. An *Application* is a program that provides some interface to interact with the Model 
2. A *Working Directory* contains files and records organized according to the Model
3. An *Instance* of the Model is an Application using a specific Working Directory
4. An *User* is a person using an Instance
5. A *Table* is a two dimensional data structure with unique row and column headers, and will always have the same data structure in each column
6. A *Table Type* is the description type of a Table's row headers
7. *Event*, *Match*, *Team*, *Entry*, *Scout* are specific Table Types used for FRC Scouting Data
8. A *Table Set* is a set of Tables with unique Table Types
9. A *Dataset* is a Table Set in the Working Directory used by an Instance for information analysis
10. *Configurations* represent any state of the Working Directory that is not a Dataset and that represent some arrangements affecting the state of the Instance
11. A *Version* represents the difference in Datasets and Configurations of an Instance in a certain period of time, attributed to a User
12. A *View* is a Configuration that structures and displays a Dataset in a specific way without modifying it
13. A *Script* is a Configuration written in a programming language that aids the Instance in data analysis

####  C. File System Configuration

From this point on, specific implementation details will be presented instead of generic terms listed above. The following is the structure in terms of the file system:

```text
{ApplicationExecutable}
python-embed/
	{PythonEmbedExecutable}/
		.../
			tables/
{ExternalMedia}/
	photos/
		{TeamPhotos...}
	videos/
		{MatchVideos...}
{WorkingDirectory}/
	output/
		html/
			{HTMLOutput...}
		csv/
			{CSVOutput...}
		{ExcelOutput...}
	scripts/
		{PythonScripts...}
	boards/
		{BoardFiles...}
	datasets/
		{DatasetIndexConfiguration}
		{InactiveZippedDataset...}
		{ActiveDataset...}/
			intermediate/
				{IntermediateFiles...}
			raw/
				{RawData...}
			scripts/
				{PythonScripts...}
			tba-cache/
				{TheBlueAllianceCachedData...}
			tables/
				{TableData...}
			verified/
				{VerifiedData...}
			views/
				{ViewConfiguration...}
			{BoardsConfiguration}
			{DatasetConfiguration}
			{TablesConfiguration}
			{WrongDataConfiguration}
	{ScriptConfiguration}
	{UserConfiguration}
	{CurrentDatasetConfiguration}
```

The top level directories should all be in the same parent directory in a distribution, but it is not necessary during development. The structure of the Working Directory, however, must be preserved so data can be imported

#### D. Decentralization and Versioning

1. An Instance has the ability to update its Data and Configurations when given the Working Directory from another Instance. This means Data and Configurations can be transferred between devices
2. An Instance has the ability to merge Versions when updating from another Working Directory
3. For the purposes of attributing changes and actions to the user who made them, all such changes and actions should have the ability to record their user.
4. All data meant to communicate between instances of the model must contain its source use

#### Appendix A. Python Tables Syntax Guide

```python
from tables import table
import numpy as np
import pandas as pd


# Documentation for table writing with syntax examples

# Use decorator to set the table type and calculation level

# Table type: one of {"entry", "team", "match", "event", "scout"}
# Table types do not include {"year", "district"}

# Calculation level: functions with lower level are calculated first,
# without having access to data of higher level functions
# Functions with the same level are called by the order Python imports them

@table("entry|team|match|event|scout", level=0)
def table_name(data, row):
    """
    Define the calculation function to run. There can be multiple in a file.
    Each function is called iteratively over their respect table types

    :param data: a reference object to the entire data managed by python.
    Some of this data are internal to the python runtime and some are shared
    with the Java ui. It is partially read-only and is a custom object

    :param row: a reference to the current iteration of the table. Data in
    here is always sent to Java. It is read-only and is a named tuple

    :return: a dictionary of new generated data
    """

    # get an existing column value of the current row
    print(row.column_name)

    # get the index of the row for basic information about it
    print(row.Index)

    # special columns (all base values are strings)

    # 1. event identifier (column name: 'event'/'events')
    # row.Index in event type and .event in other types
    print(
        # -- {year}{event_code}
        # uses The Blue Alliance API format
        row.event
    )

    # 2. match identifier (column name: 'match'/'matches')
    # row.Index in match type and .match in other types
    print(
        # -- {event_id}_{match_type}{match_number}
        # uses The Blue Alliance API format
        row.match,
        # sections section of above format
        row.match.number,
        row.match.m_type,
        row.match.event
    )

    # 3. team identifier (column name: 'team'/'teams')
    # row.Index in team type and .team in other types
    print(
        # -- frc{team_number}
        # uses The Blue Alliance API format
        row.team,
        # the team number
        row.team.number
    )

    # 4. scout identifier (column name: 'scout'/'scouts')
    # row.Index in scout type and .scout in other types
    print(
        # -- scout_{scout_name}
        # DOES NOT USE The Blue Alliance API format
        row.scout,
        # the scout name
        row.scout.name
    )

    # 5. entry identifier (column name: 'entry'/'entries')
    # row.Index in entry type and .entry in other types
    print(
        # -- {team_id}_{match_id}
        # DOES NOT USE The Blue Alliance API format
        row.entry,
        # sections section of above format
        row.entry.team,
        row.entry.match
    )

    # included special data columns by default for each table type

    # entry type:
    print(
        # scout_id
        row.scout,
        # -- {'red', 'blue'}
        row.alliance,
        # -- {1, 2, 3}
        row.position
    )

    # match type:
    print(
        # [entry_id]
        row.entries
    )

    # team type:
    print(
        # [match]
        row.matches
    )

    # event type:
    print(
        # [match]
        row.matches
    )
```

