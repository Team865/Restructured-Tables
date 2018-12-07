import re

import pandas as pd

__all__ = ["table"]


class EventIndex(str):
    def __getattr__(self, item):
        if item == "year":
            return int(re.findall(r"\d+", self)[0])
        if item == "event_code":
            return re.findall(r"[a-zA-Z]", self)[0]


class MatchIndex(str):
    def __getattr__(self, item):
        if item == "number":
            return int(re.findall(r"\d+", self)[1])
        if item == "match_type":
            return re.findall(r"[a-zA-Z]+", self)[1]
        if item == "event":
            return EventIndex(self.split("_")[0])


class TeamIndex(str):
    def __getattr__(self, item):
        if item == "number":
            return int(self[3:])


class ScoutIndex(str):
    def __getattr__(self, item):
        if item == "name":
            return self.split("_")[1]


class PositionIndex(str):
    def __getattr__(self, item):
        if item == "alliance":
            return self[:-1]
        if item == "number":
            return self[-1:]


class EntryIndex(str):
    def __getattr__(self, item):
        if item == "scout":
            return ScoutIndex("".join(self.split("_")[0:1]))
        if item == "team":
            return TeamIndex(self.split("_")[2])
        if item == "position":
            return PositionIndex(self.split("_")[3])
        if item == "match":
            return MatchIndex("".join(self.split("_")[4:5]))


_pool = []


def table(rows, level):
    def table_factory(row_func):
        _pool.append((row_func, rows.lower().strip(), level))
        return None

    return table_factory


class _DatasetTables():
    def __init__(self):
        # todo load previous data
        self.entries = pd.DataFrame()
        self.teams = pd.DataFrame()
        self.matches = pd.DataFrame()
        self.scouts = pd.DataFrame()
        self.events = pd.DataFrame()
        self.entries_h = pd.DataFrame()
        self.teams_h = pd.DataFrame()
        self.matches_h = pd.DataFrame()
        self.scouts_h = pd.DataFrame()
        self.events_h = pd.DataFrame()


def _exec_env():
    exec_pool = sorted(_pool, key=lambda x: x[2])
    level = 0
    t = _DatasetTables()

    def data(*args):
        arg_col = []
        for arg in args:
            try:
                arg_col.extend(arg)
            except TypeError:
                arg_col.append(arg)
        arg_type = arg_col[0]
        return t.entries_h[arg_col]

    data.entries = t.entries.index.values
    data.teams = t.teams.index.values
    data.matches = t.matches.index.values
    data.scouts = t.scouts.index.values
    data.events = t.events.index.values

    entry_pool = []
    team_pool = []
    match_pool = []
    scout_pool = []
    events_pool = []

    def data_gen(f0, it0, r0):
        for tup in it0.itertuples(index=True, name=r0):
            if r0 == "entry":
                tup.Index = EntryIndex(tup.Index)
            elif r0 == "team":
                tup.Index = TeamIndex(tup.Index)
            elif r0 == "match":
                tup.Index = MatchIndex(tup.Index)
            elif r0 == "event":
                tup.Index = EventIndex(tup.Index)
            elif r0 == "scout":
                tup.Index = ScoutIndex(tup.Index)
            yield f0(data, tup)

    for f, r, l in exec_pool:
        if level != l:
            entry_pool.clear()
            team_pool.clear()
            match_pool.clear()
            scout_pool.clear()
            events_pool.clear()
        level = l
        if r == "entry":
            it = t.entries
        elif r == "team":
            it = t.teams
        elif r == "match":
            it = t.matches
        elif r == "event":
            it = t.events
        elif r == "scout":
            it = t.scouts
        else:
            continue
        new_frame = pd.DataFrame(data_gen(f, it, r))
        if r == "entry":
            entry_pool.append(new_frame)
        elif r == "team":
            team_pool.append(new_frame)
        elif r == "match":
            match_pool.append(new_frame)
        elif r == "event":
            events_pool.append(new_frame)
        elif r == "scout":
            scout_pool.append(new_frame)


if __name__ == '__main__':
    _exec_env()
