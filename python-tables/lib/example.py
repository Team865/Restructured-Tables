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

